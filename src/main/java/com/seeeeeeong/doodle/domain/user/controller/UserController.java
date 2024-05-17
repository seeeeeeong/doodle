package com.seeeeeeong.doodle.domain.user.controller;

import com.seeeeeeong.doodle.common.response.SuccessResponse;
import com.seeeeeeong.doodle.domain.user.dto.ResponseJwtToken;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinRequest;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinResponse;
import com.seeeeeeong.doodle.domain.user.dto.UserLoginRequest;
import com.seeeeeeong.doodle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SuccessResponse<UserJoinResponse>> join(@RequestBody UserJoinRequest request) {
        return SuccessResponse.of(userService.join(request.getUserName(), request.getPassword())).asHttp(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<ResponseJwtToken>> login(@RequestBody UserLoginRequest request) {
        return SuccessResponse.of(userService.login(request.getUserName(), request.getPassword())).asHttp(HttpStatus.OK);
    }
}
