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
import ru.hackaton.backend.models.domain.User;
import ru.hackaton.backend.models.domain.UserRole;
import ru.hackaton.backend.models.domain.views.UserTestView;
import ru.hackaton.backend.repositories.UserRepository;
import ru.hackaton.backend.repositories.views.UserTestViewRepository;
import ru.hackaton.backend.util.PageWrapper;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final static String DEFAULT_SORT_OPTION = "createdAt";

    private final UserRepository userRepository;

    private final UserTestViewRepository userTestViewRepository;

    private final UserMapper userMapper;


//    private final UserTestMapper userTestMapper;

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


    @Transactional
    public void updateUserTest(long userId, long testId, UserTestDto userTestDto) {
        userRepository.deleteUserTest(userId, testId);

        //Т.к. у нас нет мапера на UserTestDto, мы игнорируем
        // поле UserTestDto.passedAt и получаем текущую дату через LocalDateTime.now()
        userRepository.addUserTest(userId, testId, userTestDto.getScore(), LocalDateTime.now());
    }

    public void deleteUserTest(long userId, long testId) {
        userRepository.deleteUserTest(userId, testId);
    }

    public PageWrapper<UserTestView> getAllUserTests(long userId, Integer pageNum, Integer perPage, String artName) {
        perPage = Math.min(perPage, 100);
        Pageable pageable = PageRequest.of(pageNum, perPage, Sort.by(Sort.Direction.DESC, "updated_at"));

        Page<UserTestView> page;
        if (artName == null)
            page = userTestViewRepository.findAll(userId, pageable);
        else
            page = userTestViewRepository.findAllWhereArtNameEquals(userId, artName.toLowerCase(), pageable);

        return new PageWrapper<>(page.getTotalElements(), page.getContent());
    }

    public int getUserPosition(long userId) {
        return userRepository.getUserPosition(userId);
    }

}
