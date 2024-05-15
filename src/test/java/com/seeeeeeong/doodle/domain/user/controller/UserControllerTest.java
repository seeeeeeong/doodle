package com.seeeeeeong.doodle.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeeeeeong.doodle.common.exception.DoodleApplicationException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinRequest;
import com.seeeeeeong.doodle.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
    public void 회원가입() throws Exception{
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
        result.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void 회원가입시_이미_회원가입된_userName으로_회원가입_하는경우_에러반환() throws Exception{
        // given
        final String userName = "userName";
        final String password = "password";

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userName", userName);
        requestMap.put("password", password);
        String requestBody = objectMapper.writeValueAsString(requestMap);

        // when
        when(userService.join(userName, password)).thenThrow(new DoodleApplicationException(ErrorCode.DUPLICATED_USER_NAME));

        ResultActions result = mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        result.andExpect(status().is(ErrorCode.DUPLICATED_USER_NAME.getHttpStatus().value()));
    }

    @Test
    @WithAnonymousUser
    public void 로그인() throws Exception {
        String userName = "userName";
        String password = "password";

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
    public void 로그인시_회원가입한적이_없다면_에러발생() throws Exception {
        String userName = "userName";
        String password = "password";

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userName", userName);
        requestMap.put("password", password);
        String requestBody = objectMapper.writeValueAsString(requestMap);

        // when
        when(userService.login(userName, password)).thenThrow(new DoodleApplicationException(ErrorCode.USER_NOT_FOUND));

        ResultActions result = mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        result.andExpect(status().is(ErrorCode.USER_NOT_FOUND.getHttpStatus().value()));

    }

    @Test
    @WithAnonymousUser
    public void 로그인시_비밀번호가_다르면_에러발생() throws Exception {
        String userName = "userName";
        String password = "password";

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userName", userName);
        requestMap.put("password", password);
        String requestBody = objectMapper.writeValueAsString(requestMap);

        // when
        when(userService.login(userName, password)).thenThrow(new DoodleApplicationException(ErrorCode.INVALID_PASSWORD));

        ResultActions result = mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        result.andExpect(status().is(ErrorCode.INVALID_PASSWORD.getHttpStatus().value()));

    }
}
