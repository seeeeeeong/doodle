package com.seeeeeeong.doodle.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeeeeeong.doodle.common.controller.ControllerTestSetup;
import com.seeeeeeong.doodle.common.exception.BusinessException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.common.security.jwt.JwtTokenProvider;
import com.seeeeeeong.doodle.domain.post.dto.CreatePostResponse;
import com.seeeeeeong.doodle.domain.post.dto.PostResponse;
import com.seeeeeeong.doodle.domain.post.service.PostService;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest extends ControllerTestSetup {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private final String title = "title";
    private final String body = "body";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postService)).build();
    }

    @Test
    public void createPost() throws Exception {
        // given
        Long userId = 1L;

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("title", title);
        requestMap.put("body", body);
        String requestBody = objectMapper.writeValueAsString(requestMap);
        String accessToken = "Bearer " + jwtTokenProvider.createAccessToken(userId, UserRole.USER);

        // when
        when(postService.createPost(userId, title, body)).thenReturn(new CreatePostResponse(userId));
        ResultActions result = mockMvc.perform(post("/api/v1/posts")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());
    }

    @Test
    public void list() throws Exception {
        // given
        Long userId = 1L;
        String accessToken = "Bearer " + jwtTokenProvider.createAccessToken(userId, UserRole.USER);
        int size = 20;
        int page = 0;
        Sort.Direction direction = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(direction,"post_id"));
        List<PostResponse> posts = new ArrayList<>();
        posts.add(new PostResponse(1L, title, body, User.create("user1", "password")));
        posts.add(new PostResponse(2L, title, body, User.create("user2", "password")));

        // when
        when(postService.list(anyInt(), anyInt(), any(Sort.Direction.class))).thenReturn(new PageImpl<>(posts, pageable, 2));
        ResultActions result = mockMvc.perform(get("/api/v1/posts")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .header("Authorization", accessToken)
                .queryParam("size", String.valueOf(size))
                .queryParam("page", String.valueOf(page))
                .queryParam("direction", "ASC"));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].postId").value(posts.get(0).getPostId()))
                .andExpect(jsonPath("$.data.totalPages").value(1))
                .andExpect(jsonPath("$.data.totalElements").value(2))
                .andExpect(jsonPath("$.data.pageable").exists())
                .andExpect(jsonPath("$.data.size").value(size))
                .andExpect(jsonPath("$.data.number").value(page));
    }

    @Test
    public void myPosts() throws Exception {
        // given
        Long userId = 1L;
        String accessToken = "Bearer " + jwtTokenProvider.createAccessToken(userId, UserRole.USER);
        int size = 20;
        int page = 0;
        Sort.Direction direction = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(direction,"post_id"));
        List<PostResponse> posts = new ArrayList<>();
        User user = User.create("userName", "password");
        posts.add(new PostResponse(1L, title, body, user));

        // when
        when(postService.myPosts(anyLong(), anyInt(), anyInt(), any(Sort.Direction.class))).thenReturn(new PageImpl<>(posts, pageable, 1));
        ResultActions result = mockMvc.perform(get("/api/v1/posts/my")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .header("Authorization", accessToken)
                .queryParam("size", String.valueOf(size))
                .queryParam("page", String.valueOf(page))
                .queryParam("direction", "ASC"));

        // then
        result.andExpect(status().isOk());
    }

    @Test
    public void updatePost() throws Exception {
        // given
        Long userId = 1L;
        User user = User.create("userName", "password");

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("title", title);
        requestMap.put("body", body);
        String requestBody = objectMapper.writeValueAsString(requestMap);
        String accessToken = "Bearer " + jwtTokenProvider.createAccessToken(userId, UserRole.USER);

        List<PostResponse> posts = new ArrayList<>();
        posts.add(new PostResponse(1L, title, body, user));

        // when
        ResultActions result = mockMvc.perform(put("/api/v1/posts/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));


        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deletePost() throws Exception {
        // given
        Long userId = 1L;
        User user = User.create("userName", "password");

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("title", title);
        requestMap.put("body", body);
        String requestBody = objectMapper.writeValueAsString(requestMap);
        String accessToken = "Bearer " + jwtTokenProvider.createAccessToken(userId, UserRole.USER);

        List<PostResponse> posts = new ArrayList<>();
        posts.add(new PostResponse(1L, title, body, user));

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/posts/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));


        // then
        result.andExpect(status().isNoContent());
    }
}
