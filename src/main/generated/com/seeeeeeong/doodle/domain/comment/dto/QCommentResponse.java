package com.seeeeeeong.doodle.domain.comment.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.seeeeeeong.doodle.domain.comment.dto.QCommentResponse is a Querydsl Projection type for CommentResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCommentResponse extends ConstructorExpression<CommentResponse> {

    private static final long serialVersionUID = 516819809L;

    public QCommentResponse(com.querydsl.core.types.Expression<Long> commentId, com.querydsl.core.types.Expression<String> comment, com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> userName, com.querydsl.core.types.Expression<Long> postId, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> updatedAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> deletedAt) {
        super(CommentResponse.class, new Class<?>[]{long.class, String.class, long.class, String.class, long.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, commentId, comment, userId, userName, postId, createdAt, updatedAt, deletedAt);
    }

}

