package com.seeeeeeong.doodle.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seeeeeeong.doodle.domain.comment.dto.CommentResponse;
import com.seeeeeeong.doodle.domain.comment.dto.QCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.seeeeeeong.doodle.domain.comment.domain.QComment.comment1;
import static com.seeeeeeong.doodle.domain.post.domain.QPost.post;
import static com.seeeeeeong.doodle.domain.user.domain.QUser.user;


@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<CommentResponse> getComments(Long postId, Pageable pageable, Sort.Direction direction) {

        List<CommentResponse> content = queryFactory.select(
                new QCommentResponse(comment1.commentId, comment1.comment, user.userId, user.userName, post.postId, comment1.createdAt, comment1.updatedAt, comment1.deletedAt))
                .from(comment1)
                .where(comment1.post.postId.eq(postId))
                .leftJoin(user).on(user.userId.eq(comment1.user.userId))
                .orderBy(direction.isAscending() ? comment1.createdAt.asc() : comment1.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(comment1.count()).from(comment1);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
