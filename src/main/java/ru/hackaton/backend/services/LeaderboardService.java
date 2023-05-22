package ru.hackaton.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.UserDto;
import ru.hackaton.backend.mappers.UserMapper;
import ru.hackaton.backend.models.domain.User;
import ru.hackaton.backend.repositories.UserRepository;
import ru.hackaton.backend.util.PageWrapper;

@Service
@RequiredArgsConstructor
public class LeaderboardService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public PageWrapper<UserDto> getTopUsers(int limit) {

        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "score"));

        Page<User> page = userRepository.findAll(pageable);

        return new PageWrapper<>(page.getTotalElements(), userMapper.mapToLeaderboardList(page.getContent()));
    }
}
