package com.seeeeeeong.doodle.domain.comment.repository;

import com.seeeeeeong.doodle.domain.comment.dto.CommentResponse;
import com.seeeeeeong.doodle.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentQueryRepository {

    Page<CommentResponse> getComments(Long postId, Pageable pageable, Sort.Direction direction);

}
