package com.seeeeeeong.doodle.domain.user.controller;

import com.seeeeeeong.doodle.common.response.Response;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinRequest;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinResponse;
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

    // TODO : implement
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        UserJoinResponse response = userService.join(request);
        return Response.success(response);
    }
}
