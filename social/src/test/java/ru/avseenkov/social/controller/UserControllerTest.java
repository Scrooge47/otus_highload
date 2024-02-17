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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.avseenkov.social.dto.UserDto;
import ru.avseenkov.social.model.User;
import ru.avseenkov.social.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebAppConfiguration
class UserControllerTest {

    @Autowired
    private WebApplicationContext applicationContext;

    @MockBean
    UserService userService;

    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    User user;

    UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setFirstName("name");
        userDto.setLastName("surname");


    }

    @BeforeAll
    public void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
    }

    @Test
    @WithMockUser
    void getUser() throws Exception {
        Mockito.when(userService.getUser(Mockito.anyLong())).thenReturn(userDto);
        mvc.perform(MockMvcRequestBuilders.get("/user/get/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(userDto)));
    }

    @Test
    @WithMockUser
    void getUsersByFirstNameAndLastName() throws Exception {
        List<UserDto> users = new ArrayList<>();
        users.add(userDto);
        Mockito.when(userService.findUserByFirstNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(users);
        mvc.perform(MockMvcRequestBuilders.get("/user/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("first_name", "test")
                        .param("last_name", "test"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(users)));
    }
}