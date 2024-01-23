package ru.avseenkov.social.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.avseenkov.social.dto.JwtResponse;
import ru.avseenkov.social.dto.LoginRequest;
import ru.avseenkov.social.dto.RegisterRequest;
import ru.avseenkov.social.service.AuthService;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/user/register")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse signUp(@RequestBody @Valid RegisterRequest request) {
        return authService.signup(request);
    }

}
