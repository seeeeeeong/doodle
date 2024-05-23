package com.seeeeeeong.doodle.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeeeeeong.doodle.common.controller.ControllerTestSetup;
import com.seeeeeeong.doodle.common.exception.BusinessException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.common.security.jwt.JwtTokenProvider;
import com.seeeeeeong.doodle.domain.alarm.service.AlarmService;
import com.seeeeeeong.doodle.domain.comment.dto.CommentResponse;
import com.seeeeeeong.doodle.domain.post.controller.PostController;
import com.seeeeeeong.doodle.domain.post.dto.PostResponse;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.domain.UserRole;
import com.seeeeeeong.doodle.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends ControllerTestSetup {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AlarmService alarmService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService, alarmService)).build();
    }

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

    @Test
    public void alarm() throws Exception {
        // given
        Long userId = 1L;
        String title = "title";
        String body = "body";
        String accessToken = "Bearer " + jwtTokenProvider.createAccessToken(userId, UserRole.USER);
        int size = 20;
        int page = 0;
        Sort.Direction direction = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(direction,"post_id"));
        List<PostResponse> posts = new ArrayList<>();
        User user = User.create("userName", "password");
        posts.add(new PostResponse(1L, title, body, user));

        List<CommentResponse> comments = new ArrayList<>();
        comments.add(new CommentResponse(1L, "comment", userId, user.getUserName(), posts.get(0).getPostId(), LocalDateTime.now(), null, null));

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/users/alarm")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .header("Authorization", accessToken)
                .queryParam("size", String.valueOf(size))
                .queryParam("page", String.valueOf(page))
                .queryParam("direction", "ASC"));

        // then
        result.andExpect(status().isOk());
    }


}
