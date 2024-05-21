package com.seeeeeeong.doodle.domain.post.domain;

import com.seeeeeeong.doodle.common.entity.BaseEntityWithUpdate;
import com.seeeeeeong.doodle.domain.like.domain.Like;
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
@Table(name = "\"post\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE \"post\" SET deleted_at = current_timestamp where post_id = ?")
@Where(clause = "deleted_at is NULL")
public class Post extends BaseEntityWithUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String body;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private List<Like> likes;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private Post(User user, String title, String body) {
        this.user = user;
        this.title = title;
        this.body = body;
    }

    public static Post of(User user, String title, String body) {
        return new Post(user, title, body);
    }

    public void updatePost(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
