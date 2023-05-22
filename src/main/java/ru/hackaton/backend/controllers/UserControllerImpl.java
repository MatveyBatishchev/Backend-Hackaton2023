package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.UserDto;
import ru.hackaton.backend.dtos.UserTestDto;
import ru.hackaton.backend.models.domain.UserRole;
import ru.hackaton.backend.models.domain.views.UserTestView;
import ru.hackaton.backend.services.UserService;
import ru.hackaton.backend.util.PageWrapper;

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
    public void updateTestResult(long userId, long testId, UserTestDto userTestDto) {
        userService.updateUserTestResult(userId, testId, userTestDto);
    }

    @Override
    public void deleteTestResult(long userId, long testId) {
        userService.deleteUserTestResult(userId, testId);
    }

    @Override
    public PageWrapper<UserTestView> readAllTests(long userId, Integer pageNum, Integer perPage, String artName) {
        return userService.getAllTests(userId, pageNum, perPage, artName);
    }
}
