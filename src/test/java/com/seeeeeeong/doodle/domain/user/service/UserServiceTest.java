package com.seeeeeeong.doodle.domain.user.service;

import com.seeeeeeong.doodle.common.exception.DoodleApplicationException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.dto.UserJoinResponse;
import com.seeeeeeong.doodle.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void 회원가입이_정상적으로_동작하는_경우() {
        // given
        String userName = "userName";
        String password = "password";

        User user = User.create(userName, password);

        // when
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // then
        assertDoesNotThrow(() -> userService.join(userName, password));

    }

    @Test
    void 회원가입이_userName으로_회원가입한_유저가_이미_있는경우() {
        // given
        String userName = "userName";
        String password = "password";

        User user = User.create(userName, password);

        // when
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        DoodleApplicationException exception = assertThrows(DoodleApplicationException.class,
                () -> userService.join(userName, password));

        assertEquals(ErrorCode.DUPLICATED_USER_NAME, exception.getErrorCode());
    }

}
