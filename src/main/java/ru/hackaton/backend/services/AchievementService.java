package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.AchievementDto;
import ru.hackaton.backend.dtos.UserTestDto;
import ru.hackaton.backend.mappers.AchievementMapper;
import ru.hackaton.backend.models.domain.Achievement;
import ru.hackaton.backend.models.domain.AchievementType;
import ru.hackaton.backend.models.domain.User;
import ru.hackaton.backend.repositories.AchievementRepository;
import ru.hackaton.backend.repositories.TestRepository;
import ru.hackaton.backend.repositories.UserRepository;
import ru.hackaton.backend.repositories.UserTestRepository;
import ru.hackaton.backend.util.AchievementCategory;
import ru.hackaton.backend.util.AchievementResponse;
import ru.hackaton.backend.util.PageWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.hackaton.backend.models.domain.AchievementType.*;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;

    private final UserRepository userRepository;

    private final UserTestRepository userTestRepository;

    private final TestRepository testRepository;

    private final AchievementMapper achievementMapper;

    private HashMap<AchievementType, Predicate<List<UserTestDto>>> testAchievementsChecks;

    private HashMap<AchievementType, Predicate<User>> profileAchievementsChecks;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository, UserRepository userRepository, UserTestRepository userTestRepository, TestRepository testRepository, AchievementMapper achievementMapper) {
        this.achievementRepository = achievementRepository;
        this.userRepository = userRepository;
        this.userTestRepository = userTestRepository;
        this.testRepository = testRepository;
        this.achievementMapper = achievementMapper;
        initializeAchievementsChecks();
    }

    private void initializeAchievementsChecks() {

        testAchievementsChecks = new HashMap<>() {{
            put(TEST_MORE_1, (userTests) -> userTests.size() >= 1);

            put(TEST_MORE_10, (userTests) -> userTests.size() >= 10);

            put(TEST_MORE_20, (userTests) -> userTests.size() >= 20);

            put(TEST_MAX_SCORE, (userTests) -> {
                UserTestDto userTest = userTests.get(0);
                return  testRepository.findScoreSumByTestId(userTest.getTestId()) == userTest.getScore().longValue();
            });

            put(TEST_3_IN_ROW_MAX_SCORE, (userTests) -> {
                for (int i = 0; i < 3; i++) {
                    if (testRepository.findScoreSumByTestId(userTests.get(i).getTestId()) != userTests.get(i).getScore().longValue())
                        return false;
                }
                return true;
            });
        }};

        profileAchievementsChecks = new HashMap<>() {{
            put(PROFILE_MORE_100_POINTS, (user) -> user.getScore() >= 100);
        }};
    }

    public List<AchievementResponse> checkUserAchievements(long userId, AchievementCategory achievementCategory) {
        List<Achievement> userAchievements = achievementRepository.findAllByUserId(userId);
        Set<AchievementType> userAchievementTypes = userAchievements
                .stream()
                .map(Achievement::getAchievementType)
                .collect(Collectors.toSet());

        List<AchievementResponse> responses = new ArrayList<>();
        switch (achievementCategory) {
            case TEST -> {
                Set<AchievementType> testAchievements = AchievementCategory.getTestTypes();
                testAchievements.removeAll(userAchievementTypes);

                List<UserTestDto> userTests = userTestRepository.getUserTestByUserId(userId);

                for (AchievementType achievementType : testAchievements) {
                    if (testAchievementsChecks.get(achievementType).test(userTests))
                        responses.add(rewardUserWithAchievement(userId, achievementType));
                }
            }
        }

        Set<AchievementType> testAchievements = AchievementCategory.getProfileTypes();
        testAchievements.removeAll(userAchievementTypes);
        if (testAchievements.size() > 0) {
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("No user with this id"));
            for (AchievementType achievementType : testAchievements) {
                if (profileAchievementsChecks.get(achievementType).test(user))
                    responses.add(rewardUserWithAchievement(userId, achievementType));
            }
        }

        return responses;
    }

    private AchievementResponse rewardUserWithAchievement(long userId, AchievementType achievementType) {
        Achievement achievement = achievementRepository.findByAchievementType(achievementType);
        achievementRepository.addAchievementToUser(userId, achievement.getId());
        return new AchievementResponse(
                userId, achievement.getId(), achievement.getImage(),
                achievement.getTitle(), achievement.getSuccessInfo());
    }

    public PageWrapper<AchievementDto> getAllAchievements(long userId) {
        Page<Achievement> page = achievementRepository.findAllWithUserReceived(userId, PageRequest.of(0, Integer.MAX_VALUE));
        return new PageWrapper<>(page.getTotalElements(), achievementMapper.mapToList(page.getContent()));
    }

}
