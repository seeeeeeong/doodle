package com.seeeeeeong.doodle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeeeeeong.doodle.controller.request.UserJoinRequest;
import com.seeeeeeong.doodle.controller.request.UserLoginRequest;
import com.seeeeeeong.doodle.exception.DoodleApplicationException;
import com.seeeeeeong.doodle.model.User;
import com.seeeeeeong.doodle.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void 회원가입() throws Exception{

        String userName = "userName";
        String password = "password";

        // TODO : mocking
        when(userService.join(userName, password)).thenReturn(mock(User.class));

        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                // TODO : add request body
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
        ).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void 회원가입시_이미_회원가입된_userName으로_회원가입_하는경우_에러반환() throws Exception{

        String userName = "userName";
        String password = "password";

        // TODO : mocking
        when(userService.join(userName, password)).thenThrow(new DoodleApplicationException());

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isConflict());

    }

    @Test
    public void 로그인() throws Exception{

        String userName = "userName";
        String password = "password";

        // TODO : mocking
        when(userService.login(userName, password)).thenReturn("test_token");

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void 로그인시_회원가입이_안된_userName를_입력할경우_에러반환() throws Exception{

        String userName = "userName";
        String password = "password";

        // TODO : mocking
        when(userService.login(userName, password)).thenThrow(new DoodleApplicationException());

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void 로그인시_틀린_password를_입력할경우_에러반환() throws Exception{

        String userName = "userName";
        String password = "password";

        // TODO : mocking
        when(userService.login(userName, password)).thenThrow(new DoodleApplicationException());

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }
}
