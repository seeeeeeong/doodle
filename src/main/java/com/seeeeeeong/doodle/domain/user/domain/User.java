package com.seeeeeeong.doodle.domain.user.domain;

import com.seeeeeeong.doodle.common.entity.BaseEntityWithUpdate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "\"user\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntityWithUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    private User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static User create(String userName, String password) {
        return new User(userName, password);
    }

}
