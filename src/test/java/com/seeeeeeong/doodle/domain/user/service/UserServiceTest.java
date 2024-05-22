package com.seeeeeeong.doodle.domain.user.service;

import com.seeeeeeong.doodle.common.exception.BusinessException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.common.security.jwt.JwtTokenProvider;
import com.seeeeeeong.doodle.domain.alarm.domain.Alarm;
import com.seeeeeeong.doodle.domain.alarm.domain.AlarmArgs;
import com.seeeeeeong.doodle.domain.alarm.domain.AlarmType;
import com.seeeeeeong.doodle.domain.alarm.dto.AlarmResponse;
import com.seeeeeeong.doodle.domain.alarm.repository.AlarmRepository;
import com.seeeeeeong.doodle.domain.comment.domain.Comment;
import com.seeeeeeong.doodle.domain.comment.dto.CommentResponse;
import com.seeeeeeong.doodle.domain.post.domain.Post;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private AlarmRepository alarmRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void join() {
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
    void join_duplicated_user_name() {
        // given
        String userName = "userName";
        String password = "password";

        User user = User.create(userName, password);

        // when
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> userService.join(userName, password));

        assertEquals(ErrorCode.DUPLICATED_USER_NAME, exception.getErrorCode());
    }

    @Test
    void login() {
        // given
        String userName = "userName";
        String password = "password";
        String encodedPassword = "encodedPassword";


        User user = User.create(userName, encodedPassword);

        // when
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(jwtTokenProvider.createAccessToken(user.getUserId(), user.getRole())).thenReturn("access-token");
        when(jwtTokenProvider.createRefreshToken(user.getUserId(), user.getRole())).thenReturn("refresh-token");

        // then
        assertDoesNotThrow(() -> userService.login(userName, password));

    }

    @Test
    void login_user_not_found() {
        // given
        String userName = "userName";
        String password = "password";

        // when
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> userService.login(userName, password));

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void login_invalid_password() {
        // given
        String userName = "userName";
        String password = "password";
        String encodedPassword = "encodedPassword";

        User user = User.create(userName, encodedPassword); 

        // when
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> userService.login(userName, password));

        assertEquals(ErrorCode.INVALID_PASSWORD, exception.getErrorCode());
    }

    @Test
    void alarm() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        int size = 20;
        int page = 0;
        Sort.Direction direction = Sort.Direction.ASC;

        Post post = Post.of(user, title, body);

        Comment comment = Comment.of("comment", user, post);

        List<CommentResponse> comments = new ArrayList<>();
        comments.add(new CommentResponse(comment.getCommentId(), comment.getComment(), user.getUserId(), user.getUserName(), post.getPostId(), LocalDateTime.now(), null, null));

        Alarm alarm = Alarm.of(user, AlarmType.NEW_COMMENT_ON_POST, new AlarmArgs(user.getUserId(), 2L));

        List<AlarmResponse> alarms = new ArrayList<>();
        alarms.add(new AlarmResponse(alarm.getAlarmId(), alarm.getAlarmType(), alarm.getArgs(), LocalDateTime.now(), null, null));

        // when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(alarmRepository.alarm(user.getUserId(), Pageable.ofSize(size).withPage(page), direction)).thenReturn(new PageImpl<>(alarms));

        // then
        assertDoesNotThrow(() -> userService.alarm(user.getUserId(), size, page, direction));
    }

    @Test
    void alarm_user_not_found() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        int size = 20;
        int page = 0;
        Sort.Direction direction = Sort.Direction.ASC;

        Post post = Post.of(user, title, body);

        Comment comment = Comment.of("comment", user, post);

        List<CommentResponse> comments = new ArrayList<>();
        comments.add(new CommentResponse(comment.getCommentId(), comment.getComment(), user.getUserId(), user.getUserName(), post.getPostId(), LocalDateTime.now(), null, null));

        Alarm alarm = Alarm.of(user, AlarmType.NEW_COMMENT_ON_POST, new AlarmArgs(user.getUserId(), 2L));

        List<AlarmResponse> alarms = new ArrayList<>();
        alarms.add(new AlarmResponse(alarm.getAlarmId(), alarm.getAlarmType(), alarm.getArgs(), LocalDateTime.now(), null, null));

        // when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());
        when(alarmRepository.alarm(user.getUserId(), Pageable.ofSize(size).withPage(page), direction)).thenReturn(new PageImpl<>(alarms));

        // then
        assertDoesNotThrow(() -> userService.alarm(user.getUserId(), size, page, direction));
    }
}
