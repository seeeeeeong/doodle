package com.seeeeeeong.doodle.domain.comment.dto;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.seeeeeeong.doodle.domain.reply.dto.QReplyResponse is a Querydsl Projection type for ReplyResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReplyResponse extends ConstructorExpression<CommentResponse> {

    private static final long serialVersionUID = -1965196105L;

    public QReplyResponse(com.querydsl.core.types.Expression<Long> replyId, com.querydsl.core.types.Expression<String> reply, com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> userName, com.querydsl.core.types.Expression<Long> postId, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> updatedAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> deletedAt) {
        super(CommentResponse.class, new Class<?>[]{long.class, String.class, long.class, String.class, long.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, replyId, reply, userId, userName, postId, createdAt, updatedAt, deletedAt);
    }

}

