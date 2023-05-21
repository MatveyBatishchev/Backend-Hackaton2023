package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.UserDto;
import ru.hackaton.backend.dtos.UserTestDto;
import ru.hackaton.backend.mappers.UserMapper;
import ru.hackaton.backend.mappers.UserTestMapper;
import ru.hackaton.backend.models.domain.Test;
import ru.hackaton.backend.models.domain.User;
import ru.hackaton.backend.models.domain.UserRole;
import ru.hackaton.backend.models.domain.UserTest;
import ru.hackaton.backend.repositories.UserRepository;
import ru.hackaton.backend.util.PageWrapper;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final static String DEFAULT_SORT_OPTION = "createdAt";

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final TestService testService;

    private final UserTestMapper userTestMapper;

    private User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Пользователь с id " + id + " не была найдена!"));
    }

    public UserDto createUser(UserDto userDto) {
        User newUser = userMapper.toUser(userDto);
        return userMapper.toDto(userRepository.save(newUser));
    }

    public UserDto getUserById(long id) {
        return userMapper.toDto(findUserById(id));
    }

    public void updateUser(long id, UserDto userDto) {
        User updatedUser = userMapper.toUser(userDto);
        updatedUser.setId(id);
        updatedUser.setRoles(findUserById(id).getRoles());
        userRepository.save(updatedUser);
    }

    @Transactional
    public void updateUserRoles(long id, List<UserRole> roles) {
        userRepository.deleteRolesFromUser(id);
        userRepository.addRolesToUser(id, roles.stream().map(Enum::name).toArray(String[]::new));
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public PageWrapper<UserDto> getAllUsers(Integer pageNum, Integer perPage) {
        perPage = Math.min(perPage, 100);
        Pageable pageable = PageRequest.of(pageNum, perPage, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_OPTION));
        Page<User> page = userRepository.findAll(pageable);
        return new PageWrapper<>(page.getTotalElements(), userMapper.mapToList(page.getContent()));
    }

    public UserTestDto readUserTest(long userId, long testId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("Пользователь с id " + userId + " не был найден!"));

        //Ищем тест с заданным testId
        UserTest userTest = user.getTests().stream()
                .filter((test) -> test.getId().getTestId() == testId)
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Тест с id " + testId + " не был найден!"));

        return userTestMapper.toDto(userTest);

    }

    public void updateUserTest(long userId, long testId, UserTestDto userTestDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("Пользователь с id " + userId + " не был найден!"));

        //Ищем тест с заданным testId
//        UserTest oldUserTest = user.getTests().stream()
//                .filter((test) -> test.getId().getTestId() == testId)
//                .findFirst()
//                .orElse(null);
//
//        UserTest newUserTest = userTestMapper.toUserTest(userTestDto);
//        newUserTest.setUser(user);
//        if (oldUserTest != null) {
//            newUserTest.setTest(oldUserTest.getTest());
//        } else {
//            newUserTest.setTest(testService.findTestById(testId));
//        }
//
//        if (oldUserTest != null)
//            user.getTests().remove(oldUserTest);
//
//        user.getTests().add(newUserTest);

        UserTest userTest = userTestMapper.toUserTest(userTestDto);
        userTest.setUser(user);

        userTest.setTest(testService.findTestById(testId));

        user.getTests().add(userTest);

        userRepository.save(user);
    }

    public void deleteUserTest(long userId, long testId) {

    }

    public PageWrapper<UserTest> getAllTests(long userId) {
        return null;
    }

}
