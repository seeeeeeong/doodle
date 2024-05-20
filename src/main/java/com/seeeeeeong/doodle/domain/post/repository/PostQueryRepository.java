package com.seeeeeeong.doodle.domain.post.repository;

import com.seeeeeeong.doodle.domain.post.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PostQueryRepository {

    Page<PostResponse> getPosts(Pageable pageable,
                                Sort.Direction direction);

    Page<PostResponse> myPosts(Long userId, Pageable pageable, Sort.Direction direction);
}
