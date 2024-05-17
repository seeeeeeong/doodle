package com.seeeeeeong.doodle.domain.post.domain;

import com.seeeeeeong.doodle.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "\"post\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@SQLDelete(sql = "UPDATED \"user\" SET deleted_at = NOW() where id=?")
//@Where(clause = "deleted_at is NULL")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String body;



    private Post(User user, String title, String body) {
        this.user = user;
        this.title = title;
        this.body = body;
    }

    public static Post of(User user, String title, String body) {
        return new Post(user, title, body);
    }
}
