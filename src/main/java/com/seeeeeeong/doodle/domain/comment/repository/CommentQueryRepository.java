package com.seeeeeeong.doodle.domain.comment.repository;

import com.seeeeeeong.doodle.domain.comment.dto.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface CommentQueryRepository {

    Page<CommentResponse> getReplies(Long postId, Pageable pageable, Sort.Direction direction);

}
