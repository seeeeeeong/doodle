package com.seeeeeeong.doodle.service;

import com.seeeeeeong.doodle.exception.DoodleApplicationException;
import com.seeeeeeong.doodle.model.User;
import com.seeeeeeong.doodle.model.entity.UserEntity;
import com.seeeeeeong.doodle.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    // TODO : implement
    public User join(String userName, String password) {

        Optional<UserEntity> userEntity = userEntityRepository.findByUserName(userName);

        userEntityRepository.save(new UserEntity());

        return new User();
    }

    public String login(String userName, String password) {

        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new DoodleApplicationException());

        if(!userEntity.getPassword().equals(password)) {
            throw new DoodleApplicationException();
        }

        return "";
    }
}
