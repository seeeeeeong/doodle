package com.seeeeeeong.doodle.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeeeeeong.doodle.common.exception.BusinessException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @WithAnonymousUser
    public void join() throws Exception{
        // given
        final String userName = "userName";
        final String password = "password";

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userName", userName);
        requestMap.put("password", password);
        String requestBody = objectMapper.writeValueAsString(requestMap);

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void join_duplicated_user_name() throws Exception{
        // given
        final String userName = "userName";
        final String password = "password";

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userName", userName);
        requestMap.put("password", password);
        String requestBody = objectMapper.writeValueAsString(requestMap);

        // when
        when(userService.join(userName, password)).thenThrow(new BusinessException(ErrorCode.DUPLICATED_USER_NAME));

        ResultActions result = mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        result.andExpect(status().is(ErrorCode.DUPLICATED_USER_NAME.getStatus()));
    }

    @Test
    @WithAnonymousUser
    public void login() throws Exception {
        // given
        final String userName = "userName";
        final String password = "password";

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userName", userName);
        requestMap.put("password", password);
        String requestBody = objectMapper.writeValueAsString(requestMap);

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        result.andExpect(status().isOk());

    }

    @Test
    @WithAnonymousUser
    public void login_user_not_found() throws Exception {
        // given
        final String userName = "userName";
        final String password = "password";

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userName", userName);
        requestMap.put("password", password);
        String requestBody = objectMapper.writeValueAsString(requestMap);

        // when
        when(userService.login(userName, password)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

        ResultActions result = mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        result.andExpect(status().is(ErrorCode.USER_NOT_FOUND.getStatus()));

    }

    @Test
    @WithAnonymousUser
    public void login_invalid_password() throws Exception {
        // given
        final String userName = "userName";
        final String password = "password";

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userName", userName);
        requestMap.put("password", password);
        String requestBody = objectMapper.writeValueAsString(requestMap);

        // when
        when(userService.login(userName, password)).thenThrow(new BusinessException(ErrorCode.INVALID_PASSWORD));

        ResultActions result = mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        result.andExpect(status().is(ErrorCode.INVALID_PASSWORD.getStatus()));
    }
}
