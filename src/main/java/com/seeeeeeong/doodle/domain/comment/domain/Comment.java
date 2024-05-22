package com.seeeeeeong.doodle.domain.comment.domain;

import com.seeeeeeong.doodle.common.entity.BaseEntityWithUpdate;
import com.seeeeeeong.doodle.domain.post.domain.Post;
import com.seeeeeeong.doodle.domain.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "\"comment\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE \"comment\" SET deleted_at = current_timestamp where like_id = ?")
@Where(clause = "deleted_at is NULL")
public class Comment extends BaseEntityWithUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    private String comment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private Comment(String comment, User user, Post post) {
        this.comment = comment;
        this.user = user;
        this.post = post;
    }

    public static Comment of(String comment, User user, Post post) {
        return new Comment(comment, user, post);
    }

}
