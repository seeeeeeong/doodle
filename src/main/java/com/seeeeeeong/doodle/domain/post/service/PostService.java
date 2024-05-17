package com.seeeeeeong.doodle.domain.post.service;

import com.seeeeeeong.doodle.domain.post.repository.PostRepository;
import com.seeeeeeong.doodle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

        private final UserRepository userRepository;
        private final PostRepository postRepository;


}
