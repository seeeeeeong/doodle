package com.seeeeeeong.doodle.domain.user.service;

import com.seeeeeeong.doodle.common.exception.BusinessException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.common.security.jwt.JwtTokenProvider;
import com.seeeeeeong.doodle.domain.alarm.dto.AlarmResponse;
import com.seeeeeeong.doodle.domain.alarm.repository.AlarmRepository;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.dto.ResponseJwtToken;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinResponse;
import com.seeeeeeong.doodle.domain.user.repository.UserCacheRepository;
import com.seeeeeeong.doodle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserCacheRepository userCacheRepository;

    @Transactional
    public UserJoinResponse join(String userName, String password) {

        userRepository.findByUserName(userName).ifPresent(it -> {
            throw new BusinessException(ErrorCode.DUPLICATED_USER_NAME);
        });

        User savedUser = userRepository.save(User.create(userName, passwordEncoder.encode(password)));

        return new UserJoinResponse(savedUser.getUserId(), savedUser.getUserName());
    }

    @Transactional
    public ResponseJwtToken login(String userName, String password) {

        User user = userCacheRepository.getUser(userName).orElseGet(() ->
                userRepository.findByUserName(userName).orElseThrow(
                        () -> new BusinessException(ErrorCode.USER_NOT_FOUND)));

        userCacheRepository.setUser(user);

        if (!passwordEncoder.matches(password, user.getPassword())) {
           throw new BusinessException(ErrorCode.INVALID_PASSWORD);
       }

       String jwtAccessToken = jwtTokenProvider.createAccessToken(user.getUserId(), user.getRole());
       String jwtRefreshToken = jwtTokenProvider.createRefreshToken(user.getUserId(), user.getRole());

       return ResponseJwtToken.of(jwtAccessToken, jwtRefreshToken);
    }

    public Page<AlarmResponse> alarm(Long userId, int size, int page, Sort.Direction direction) {
        return alarmRepository.alarm(userId, Pageable.ofSize(size).withPage(page), direction);
    }

}
