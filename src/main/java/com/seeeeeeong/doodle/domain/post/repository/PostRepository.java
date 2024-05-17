package com.seeeeeeong.doodle.domain.post.repository;

import com.seeeeeeong.doodle.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
