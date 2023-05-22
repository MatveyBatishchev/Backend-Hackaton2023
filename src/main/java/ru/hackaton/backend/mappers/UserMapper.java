package ru.hackaton.backend.mappers;

import org.mapstruct.*;
import ru.hackaton.backend.dtos.UserDto;
import ru.hackaton.backend.models.domain.User;
import ru.hackaton.backend.models.domain.UserRole;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring",
        imports = {LocalDateTime.class, UserRole.class, Collections.class},
        uses = {PasswordEncoderMapper.class},
        config = IgnoreUnmappedMapperConfig.class)
public interface UserMapper {

    @Named("toUserDto")
    @Mapping(target = "roles", qualifiedByName = "mapUserRolesToList")
    UserDto toDto(User user);

    @Named("toLeaderboardDto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "score", target = "score")
    UserDto toLeaderboardDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "roles", expression = "java(Collections.singletonList(UserRole.USER))")
    User toUser(UserDto userDto);

    @IterableMapping(qualifiedByName = "toUserDto")
    List<UserDto> mapToList(Collection<User> users);

    @IterableMapping(qualifiedByName = "toLeaderboardDto")
    List<UserDto> mapToLeaderboardList(Collection<User> users);

    @Named("mapUserRolesToList")
    List<String> mapRolesToList(Collection<UserRole> userRoles);

}