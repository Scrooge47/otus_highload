package ru.avseenkov.social.service.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.avseenkov.social.dto.UserDto;
import ru.avseenkov.social.mapper.UserMapper;
import ru.avseenkov.social.model.User;
import ru.avseenkov.social.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.getUserFromDb(id);
        return UserMapper.userDtoFromUser(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found.");
        }

        return user.get();
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.getUsers();
        return UserMapper.mapToUserDto(users);
    }

    @Override
    public List<UserDto> findUserByFirstNameAndLastName(String first_name, String last_name) {
        List<User> users = userRepository.findUserByFistNameAndLastName(first_name, last_name);
        return UserMapper.mapToUserDto(users);
    }

    @Override
    public void addFriend(Long userId, Long userRequestedId) {
        User user = userRepository.getUserFromDb(userId);
        User requestedUser = userRepository.getUserFromDb(userRequestedId);

        userRepository.addFriend(user.getId(), requestedUser.getId());
    }

    @Override
    public void deleteFriend(Long userId, Long userRequestedId) {
        User user = userRepository.getUserFromDb(userId);
        User requestedUser = userRepository.getUserFromDb(userRequestedId);

        userRepository.removeFriend(user.getId(), requestedUser.getId());
    }
}
