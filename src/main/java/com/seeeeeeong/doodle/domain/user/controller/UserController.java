package com.seeeeeeong.doodle.domain.user.controller;

import com.seeeeeeong.doodle.common.response.Response;
import com.seeeeeeong.doodle.common.security.jwt.JwtToken;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinRequest;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinResponse;
import com.seeeeeeong.doodle.domain.user.dto.UserLoginRequest;
import com.seeeeeeong.doodle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        UserJoinResponse response = userService.join(request.getUserName(), request.getPassword());
        return Response.success(response);
    }

    @PostMapping("/login")
    public Response<JwtToken> login(@RequestBody UserLoginRequest request) {
        JwtToken token = userService.login(request.getUserName(), request.getPassword());
        return Response.success(token);
    }
}
