package com.seeeeeeong.doodle.domain.user.service;

import com.seeeeeeong.doodle.common.exception.DoodleApplicationException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinRequest;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinResponse;
import com.seeeeeeong.doodle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserJoinResponse join(UserJoinRequest request) {

        userRepository.findByUserName(request.getUserName()).ifPresent(it -> {
            throw new DoodleApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", request.getUserName()));
        });

        User user = userRepository.save(User.create(request.getUserName(), request.getPassword()));

        return new UserJoinResponse(user.getId(), user.getUserName());

    }

}
