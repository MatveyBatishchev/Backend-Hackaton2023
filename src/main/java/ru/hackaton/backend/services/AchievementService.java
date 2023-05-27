package ru.hackaton.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.AchievementDto;
import ru.hackaton.backend.mappers.AchievementMapper;
import ru.hackaton.backend.models.domain.Achievement;
import ru.hackaton.backend.repositories.AchievementRepository;
import ru.hackaton.backend.util.PageWrapper;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;

    private final AchievementMapper achievementMapper;

    public PageWrapper<AchievementDto> getAllAchievements(long userId) {
        Page<Achievement> page = achievementRepository.findAllWithUserReceived(userId, PageRequest.of(0, Integer.MAX_VALUE));
        return new PageWrapper<>(page.getTotalElements(), achievementMapper.mapToList(page.getContent()));
    }

}
