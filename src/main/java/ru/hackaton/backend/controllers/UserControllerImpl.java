package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.UserDto;
import ru.hackaton.backend.models.domain.UserRole;
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
    public void update(long id, List<UserRole> roles) {
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
}