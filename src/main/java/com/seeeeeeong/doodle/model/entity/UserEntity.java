package com.seeeeeeong.doodle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class UserEntity {

    @Id
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    private String password;
}
