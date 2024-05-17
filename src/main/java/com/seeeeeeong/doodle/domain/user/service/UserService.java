package com.seeeeeeong.doodle.domain.user.service;

import com.seeeeeeong.doodle.common.exception.BusinessException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.common.security.jwt.JwtTokenProvider;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.dto.ResponseJwtToken;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinResponse;
import com.seeeeeeong.doodle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserJoinResponse join(String userName, String password) {

        userRepository.findByUserName(userName).ifPresent(it -> {
            throw new BusinessException(ErrorCode.DUPLICATED_USER_NAME);
        });

        User savedUser = userRepository.save(User.create(userName, passwordEncoder.encode(password)));

        return new UserJoinResponse(savedUser.getId(), savedUser.getUserName());
    }

    @Transactional
    public ResponseJwtToken login(String userName, String password) {

       User user = userRepository.findByUserName(userName).orElseThrow(
               () -> new BusinessException(ErrorCode.USER_NOT_FOUND));

       if (!passwordEncoder.matches(password, user.getPassword())) {
           throw new BusinessException(ErrorCode.INVALID_PASSWORD);
       }

       String jwtAccessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole());
       String jwtRefreshToken = jwtTokenProvider.createRefreshToken(user.getId(), user.getRole());

       return ResponseJwtToken.of(jwtAccessToken, jwtRefreshToken);
    }

}
