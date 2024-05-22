package com.seeeeeeong.doodle.domain.user.controller;

import com.seeeeeeong.doodle.common.resolver.AuthUser;
import com.seeeeeeong.doodle.common.response.SuccessResponse;
import com.seeeeeeong.doodle.common.security.jwt.JwtTokenInfo;
import com.seeeeeeong.doodle.domain.alarm.dto.AlarmResponse;
import com.seeeeeeong.doodle.domain.user.dto.ResponseJwtToken;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinRequest;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinResponse;
import com.seeeeeeong.doodle.domain.user.dto.UserLoginRequest;
import com.seeeeeeong.doodle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/alarm")
    public ResponseEntity<SuccessResponse<Page<AlarmResponse>>> alarm(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                      @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                      @RequestParam(name = "direction", required = false, defaultValue = "DESC") Sort.Direction direction) {
        return SuccessResponse.of(userService.alarm(jwtTokenInfo.getUserId(), size, page, direction)).asHttp(HttpStatus.OK);
    }
}
