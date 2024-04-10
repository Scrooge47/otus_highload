package ru.avseenkov.social.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.avseenkov.social.dto.JwtResponse;
import ru.avseenkov.social.dto.LoginRequest;
import ru.avseenkov.social.dto.RegisterRequest;
import ru.avseenkov.social.mapper.UserMapper;
import ru.avseenkov.social.model.User;
import ru.avseenkov.social.repository.user.UserRepository;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken creds =
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword());
        Authentication auth = authenticationManager.authenticate(creds);
        String jwt = jwtService.getToken(auth.getName());
        return new JwtResponse(jwt);
    }

    public JwtResponse signup(RegisterRequest request) {
        User user = UserMapper.userFromRegisterRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        String jwt = jwtService.getToken(user.getUsername());
        return new JwtResponse(jwt);
    }
}
