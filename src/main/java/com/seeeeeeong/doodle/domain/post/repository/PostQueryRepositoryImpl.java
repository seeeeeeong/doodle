package com.seeeeeeong.doodle.domain.post.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seeeeeeong.doodle.domain.post.dto.PostResponse;
import com.seeeeeeong.doodle.domain.post.dto.QPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.seeeeeeong.doodle.domain.post.domain.QPost.post;
import static com.seeeeeeong.doodle.domain.user.domain.QUser.user;


@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostResponse> getPosts(Pageable pageable, Direction direction) {

        List<PostResponse> content = queryFactory.select(
                new QPostResponse(post.postId, post.title, post.body, post.user))
                .from(post)
                .leftJoin(user).on(user.userId.eq(post.user.userId))
                .orderBy(direction.isAscending() ? post.createdAt.asc() : post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(post.count()).from(post);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<PostResponse> myPosts(Long userId, Pageable pageable, Direction direction) {

        List<PostResponse> content = queryFactory.select(
                new QPostResponse(post.postId, post.title, post.body, post.user))
                .from(post)
                .where(post.user.userId.eq(userId))
                .orderBy(direction.isAscending() ? post.createdAt.asc() : post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(post.count()).from(post);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
