package com.seeeeeeong.doodle.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeeeeeong.doodle.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
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
                .content(requestBody)
                .with(csrf()));

        // then
        result.andExpect(status().isOk());
    }

//    @Test
//    @WithMockUser
//    public void 회원가입시_이미_회원가입된_userName으로_회원가입_하는경우_에러반환() throws Exception{
//        // given
//        final String userName = "userName";
//        final String password = "password";
//
//        Map<String, Object> requestMap = new HashMap<>();
//        requestMap.put("userName", userName);
//        requestMap.put("password", password);
//        String requestBody = objectMapper.writeValueAsString(requestMap);
//
//        // when
//        when(userService.join(mock(UserJoinRequest.class))).thenThrow(new DoodleApplicationException());
//
//        ResultActions result = mockMvc.perform(post("/api/v1/users/join")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .with(csrf()));
//
//        // then
//        result.andExpect(status().isConflict());
//    }


}
