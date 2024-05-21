package com.seeeeeeong.doodle.domain.like.repository;

import com.seeeeeeong.doodle.domain.like.domain.Like;
import com.seeeeeeong.doodle.domain.post.domain.Post;
import com.seeeeeeong.doodle.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(User user, Post post);

    @Query(value = "SELECT COUNT(*) from Like like WHERE like.post = :post")
    Integer countByPost(@Param("post") Post post);

    @Transactional
    @Modifying
    @Query("UPDATE Like like SET like.deletedAt = current_timestamp WHERE like.post =:post")
    void deleteAllByPost(@Param("post") Post post);
}
