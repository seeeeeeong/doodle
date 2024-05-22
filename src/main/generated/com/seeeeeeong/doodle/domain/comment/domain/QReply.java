package com.seeeeeeong.doodle.domain.comment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReply is a Querydsl query type for Reply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReply extends EntityPathBase<Comment> {

    private static final long serialVersionUID = 266426085L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReply reply1 = new QReply("reply1");

    public final com.seeeeeeong.doodle.common.entity.QBaseEntityWithUpdate _super = new com.seeeeeeong.doodle.common.entity.QBaseEntityWithUpdate(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final com.seeeeeeong.doodle.domain.post.domain.QPost post;

    public final StringPath reply = createString("reply");

    public final NumberPath<Long> replyId = createNumber("replyId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.seeeeeeong.doodle.domain.user.domain.QUser user;

    public QReply(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QReply(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReply(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QReply(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.seeeeeeong.doodle.domain.post.domain.QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.seeeeeeong.doodle.domain.user.domain.QUser(forProperty("user")) : null;
    }

}

