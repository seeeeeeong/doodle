//package com.seeeeeeong.doodle.domain.user.service;
//
//import com.seeeeeeong.doodle.common.exception.DoodleApplicationException;
//import com.seeeeeeong.doodle.domain.user.domain.User;
//import com.seeeeeeong.doodle.domain.user.dto.UserJoinRequest;
//import com.seeeeeeong.doodle.domain.user.repository.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Test
//    void 회원가입이_정상적으로_동작하는_경우() {
//
//        String userName = "userName";
//        String password = "password";
//
//        // mocking
//        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
//        when(userRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(userName, password)));
//
//        Assertions.assertDoesNotThrow(() -> userService.join(any(UserJoinRequest.class)));
//    }
//
//    @Test
//    void 회원가입이_userName으로_회원가입한_유저가_이미_있는경우() {
//
//        String userName = "userName";
//        String password = "password";
//        User fixture = UserEntityFixture.get(userName, password);
//
//        // mocking
//        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
//        when(userRepository.save(any())).thenReturn(Optional.of(mock(User.class)));
//
//        Assertions.assertThrows(DoodleApplicationException.class, () -> userService.join(any(UserJoinRequest.class)));
//    }
//
//}
