package com.seeeeeeong.doodle.domain.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 1518008541L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.seeeeeeong.doodle.common.entity.QBaseEntityWithUpdate _super = new com.seeeeeeong.doodle.common.entity.QBaseEntityWithUpdate(this);

    public final StringPath body = createString("body");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final ListPath<com.seeeeeeong.doodle.domain.like.domain.Like, com.seeeeeeong.doodle.domain.like.domain.QLike> likes = this.<com.seeeeeeong.doodle.domain.like.domain.Like, com.seeeeeeong.doodle.domain.like.domain.QLike>createList("likes", com.seeeeeeong.doodle.domain.like.domain.Like.class, com.seeeeeeong.doodle.domain.like.domain.QLike.class, PathInits.DIRECT2);

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final ListPath<com.seeeeeeong.doodle.domain.comment.domain.Comment, com.seeeeeeong.doodle.domain.comment.domain.QComment> replies = this.<com.seeeeeeong.doodle.domain.comment.domain.Comment, com.seeeeeeong.doodle.domain.comment.domain.QComment>createList("replies", com.seeeeeeong.doodle.domain.comment.domain.Comment.class, com.seeeeeeong.doodle.domain.comment.domain.QComment.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.seeeeeeong.doodle.domain.user.domain.QUser user;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.seeeeeeong.doodle.domain.user.domain.QUser(forProperty("user")) : null;
    }

}

