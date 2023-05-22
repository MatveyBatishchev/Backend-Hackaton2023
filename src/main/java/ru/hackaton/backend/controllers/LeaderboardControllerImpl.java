package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.UserDto;
import ru.hackaton.backend.services.LeaderboardService;
import ru.hackaton.backend.util.PageWrapper;

@RestController
@RequiredArgsConstructor
public class LeaderboardControllerImpl implements LeaderboardController {

    private final LeaderboardService leaderboardService;


    @Override
    public PageWrapper<UserDto> read(int limit) {
        return leaderboardService.getTopUsers(limit);
    }

}
