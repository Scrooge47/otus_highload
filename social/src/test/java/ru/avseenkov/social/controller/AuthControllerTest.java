package ru.avseenkov.social.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.avseenkov.social.dto.JwtResponse;
import ru.avseenkov.social.dto.LoginRequest;
import ru.avseenkov.social.dto.RegisterRequest;
import ru.avseenkov.social.dto.UserDto;
import ru.avseenkov.social.service.AuthService;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebAppConfiguration
class AuthControllerTest {

    @Autowired
    private WebApplicationContext applicationContext;

    @MockBean
    AuthService authService;

    MockMvc mvc;

    RegisterRequest request;

    UserDto userDto;

    JwtResponse jwtResponse;

    LoginRequest loginRequest;

    @Autowired
    ObjectMapper mapper;

    String token;

    @BeforeEach
    void setUp() {
        request = new RegisterRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setFirstName("name");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");


        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        jwtResponse = new JwtResponse(token);

    }

    @BeforeAll
    public void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
    }

    @Test
    void login() throws Exception {
        Mockito.when(authService.login(Mockito.any(LoginRequest.class))).thenReturn(jwtResponse);
        mvc.perform(MockMvcRequestBuilders.post("/login")
                        .content(mapper.writeValueAsString(loginRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(jwtResponse)));
    }

    @Test
    void signUp() throws Exception {
        Mockito.when(authService.signup(Mockito.any(RegisterRequest.class))).thenReturn(jwtResponse);

        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(jwtResponse)));
    }

    @Test
    void NotValidData() throws Exception {
        request.setPassword(null);
        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }
}