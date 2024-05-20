package com.seeeeeeong.doodle.domain.like.domain;

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
import java.util.List;

@Entity
@Getter
@Table(name = "\"like\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE \"like\" SET deleted_at = current_timestamp where like_id = ?")
@Where(clause = "deleted_at is NULL")
public class Like extends BaseEntityWithUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

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

    private Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public static Like of(User user, Post post) {
        return new Like(user, post);
    }
}
