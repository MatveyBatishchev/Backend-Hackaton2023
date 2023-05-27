package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hackaton.backend.dtos.CourseDto;
import ru.hackaton.backend.dtos.UserDto;
import ru.hackaton.backend.dtos.UserTestDto;
import ru.hackaton.backend.errors.UserAlreadyExistException;
import ru.hackaton.backend.mappers.CourseMapper;
import ru.hackaton.backend.mappers.UserMapper;
import ru.hackaton.backend.models.domain.*;
import ru.hackaton.backend.repositories.CourseRepository;
import ru.hackaton.backend.repositories.UserRepository;
import ru.hackaton.backend.repositories.UserTestRepository;
import ru.hackaton.backend.util.FileUploadUtil;
import ru.hackaton.backend.util.PageWrapper;
import ru.hackaton.backend.util.UploadFileResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final static String DEFAULT_SORT_OPTION = "createdAt";

    private final UserRepository userRepository;

    private final UserTestRepository userTestRepository;

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final UserMapper userMapper;

    private final FileUploadUtil fileUploadUtil;


//    private final UserTestMapper userTestMapper;

    private User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Пользователь с id " + id + " не была найдена!"));
    }

    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail()))
            throw new UserAlreadyExistException("There is already an account with that email address: " + userDto.getEmail());

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
    public UploadFileResponse uploadUserAvatar(long id, MultipartFile file) {
        if (userRepository.existsById(id)) {
            UploadFileResponse uploadFileResponse = fileUploadUtil.saveUserAvatar(id, file);
            userRepository.updateUserAvatar(id, uploadFileResponse.getFileDetails().getFileUri());
            return uploadFileResponse;
        } else {
            throw new EntityNotFoundException("Пользователь с id " + id + " не была найдена!");
        }
    }

    @Transactional
    public void updateUserTest(long userId, long testId, UserTestDto userTestDto) {
        userRepository.deleteUserTest(userId, testId);

        //Т.к. у нас нет мапера на UserTestDto, мы игнорируем
        // поле UserTestDto.passedAt и получаем текущую дату через LocalDateTime.now()
        userRepository.addUserTest(userId, testId, userTestDto.getScore(), LocalDateTime.now());
    }

    @Transactional
    public void deleteUserTest(long userId, long testId) {
        userRepository.deleteUserTest(userId, testId);
    }

    public PageWrapper<UserTest> getAllUserTests(long userId, Integer pageNum, Integer perPage, Long artId) {
        perPage = Math.min(perPage, 100);
        Pageable pageable = PageRequest.of(pageNum, perPage, Sort.by(Sort.Direction.DESC, "updated_at"));

        Page<UserTest> page;
        if (artId == null)
            page = userTestRepository.findAll(userId, pageable);
        else
            page = userTestRepository.findAllWhereArtIdEquals(userId, artId, pageable);

        return new PageWrapper<>(page.getTotalElements(), page.getContent());
    }

    public int getUserPosition(long userId) {
        return userRepository.getUserPosition(userId);
    }

    public long getUserTestsCount(long userId, Long artId) {
        if (artId != null)
            return userTestRepository.findCount(userId, artId);

        return userTestRepository.findCount(userId);
    }

    public long getUserTestsScoreSum(long userId, Long artId) {
        if (artId != null)
            return userTestRepository.findScoreSum(userId, artId);

        return userTestRepository.findScoreSum(userId);
    }

    public List<CourseDto> getUserCourses(long userId) {
        User user = findUserById(userId);

        Set<UserCourse> userCourses = user.getCourses();


        return courseMapper.toDtoList(
                userCourses.stream()
                        .map(UserCourse::getCourse)
                        .collect(Collectors.toList()));

    }

    public void addUserCourse(long userId, long courseId) {
        User user = findUserById(userId);
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new EntityNotFoundException("Курс с id %d не был найден!".formatted(courseId)));

        user.addCourse(course);

        userRepository.save(user);

    }

    public void deleteUserCourse(long userId, long courseId) {
        User user = findUserById(userId);
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new EntityNotFoundException("Курс с id %d не был найден!".formatted(courseId)));

        user.removeCourse(course);

        userRepository.save(user);

    }

}
