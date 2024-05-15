package com.seeeeeeong.doodle.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;

@Entity
@Getter
@Table(name = "\"user\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@SQLDelete(sql = "UPDATED \"user\" SET deleted_at = NOW() where id=?")
//@Where(clause = "deleted_at is NULL")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    private User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static User create(String userName, String password) {
        return new User(userName, password);
    }

}
