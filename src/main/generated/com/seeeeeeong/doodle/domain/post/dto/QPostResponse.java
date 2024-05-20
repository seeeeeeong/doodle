package com.seeeeeeong.doodle.domain.post.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.seeeeeeong.doodle.domain.post.dto.QPostResponse is a Querydsl Projection type for PostResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostResponse extends ConstructorExpression<PostResponse> {

    private static final long serialVersionUID = -1355790467L;

    public QPostResponse(com.querydsl.core.types.Expression<Long> postId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> body, com.querydsl.core.types.Expression<? extends com.seeeeeeong.doodle.domain.user.domain.User> user) {
        super(PostResponse.class, new Class<?>[]{long.class, String.class, String.class, com.seeeeeeong.doodle.domain.user.domain.User.class}, postId, title, body, user);
    }

}

