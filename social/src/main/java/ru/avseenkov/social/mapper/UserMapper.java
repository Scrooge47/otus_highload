package ru.avseenkov.social.mapper;

import ru.avseenkov.social.dto.RegisterRequest;
import ru.avseenkov.social.dto.UserDto;
import ru.avseenkov.social.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User userFromRegisterRequest(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setInterest(request.getInterest());
        user.setCity(request.getCity());
        user.setAge(request.getAge());
        return user;
    }

    public static UserDto userDtoFromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setGender(user.getGender());
        userDto.setInterest(user.getInterest());
        userDto.setCity(user.getCity());
        userDto.setAge(user.getAge());
        return userDto;
    }

    public static List<UserDto> mapToUserDto(Iterable<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(userDtoFromUser(user));
        }
        return userDtos;
    }

}
