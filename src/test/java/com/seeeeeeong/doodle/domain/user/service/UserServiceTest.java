package com.seeeeeeong.doodle.domain.user.service;

import com.seeeeeeong.doodle.common.exception.BusinessException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.common.security.jwt.JwtTokenProvider;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

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

        BusinessException exception = assertThrows(BusinessException.class,
                () -> userService.join(userName, password));

        assertEquals(ErrorCode.DUPLICATED_USER_NAME, exception.getErrorCode());
    }

    @Test
    void 로그인이_정상적으로_동작하는_경우() {
        // given
        String userName = "userName";
        String password = "password";
        String encodedPassword = "encodedPassword";


        User user = User.create(userName, encodedPassword);

        // when
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(jwtTokenProvider.createAccessToken(user.getId(), user.getRole())).thenReturn("access-token");
        when(jwtTokenProvider.createRefreshToken(user.getId(), user.getRole())).thenReturn("refresh-token");

        // then
        assertDoesNotThrow(() -> userService.login(userName, password));

    }

    @Test
    void 로그인시_유저가_존재하지_않는경우() {
        // given
        String userName = "userName";
        String password = "password";

        // when
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        // then
        assertThrows(BusinessException.class, () -> userService.login(userName, password));

    }

    @Test
    void 로그인시_비밀번호가_일치하지_않는경우() {
        // given
        String userName = "userName";
        String password = "password";
        String encodedPassword = "encodedPassword";

        User user = User.create(userName, encodedPassword); 

        // when
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        // then
        assertThrows(BusinessException.class, () -> userService.login(userName, password));

    }

}
