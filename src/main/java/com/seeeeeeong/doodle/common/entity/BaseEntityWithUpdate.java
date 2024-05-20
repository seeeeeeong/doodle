package com.seeeeeeong.doodle.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntityWithUpdate extends BaseEntity{

    @NotNull
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
