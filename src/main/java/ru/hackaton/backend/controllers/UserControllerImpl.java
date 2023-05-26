package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.hackaton.backend.dtos.UserDto;
import ru.hackaton.backend.dtos.UserTestDto;
import ru.hackaton.backend.models.domain.UserRole;
import ru.hackaton.backend.models.domain.UserTest;
import ru.hackaton.backend.services.UserService;
import ru.hackaton.backend.util.PageWrapper;
import ru.hackaton.backend.util.UploadFileResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public UserDto create(UserDto userDto) {
        return userService.createUser(userDto);
    }

    @Override
    public UserDto read(long id) {
        return userService.getUserById(id);
    }

    @Override
    public void update(long id, UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @Override
    public void updateUserRoles(long id, List<UserRole> roles) {
        userService.updateUserRoles(id, roles);
    }

    @Override
    public void delete(long id) {
        userService.deleteUserById(id);
    }

    @Override
    public PageWrapper<UserDto> readAll(Integer pageNum, Integer perPage) {
        return userService.getAllUsers(pageNum, perPage);
    }

    @Override
    public UploadFileResponse uploadAvatar(long id, MultipartFile file) {
        return userService.uploadUserAvatar(id, file);
    }

    @Override
    public void updateUserTest(long userId, long testId, UserTestDto userTestDto) {
        userService.updateUserTest(userId, testId, userTestDto);
    }

    @Override
    public void deleteUserTest(long userId, long testId) {
        userService.deleteUserTest(userId, testId);
    }

    @Override
    public PageWrapper<UserTest> readAllUserTests(long userId, Integer pageNum, Integer perPage, Long artId) {
        return userService.getAllUserTests(userId, pageNum, perPage, artId);
    }

    @Override
    public int getUserPosition(long userId) {
        return userService.getUserPosition(userId);
    }

    @Override
    public long getUserTestsCount(long userId, Long artId) {
        return userService.getUserTestsCount(userId, artId);
    }

    @Override
    public long getUserTestsScoreSum(long userId, Long artId) {
        return userService.getUserTestsScoreSum(userId, artId);
    }
}
