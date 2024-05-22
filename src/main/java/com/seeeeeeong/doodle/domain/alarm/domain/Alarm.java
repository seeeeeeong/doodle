package com.seeeeeeong.doodle.domain.alarm.domain;

import com.seeeeeeong.doodle.common.entity.BaseEntityWithUpdate;
import com.seeeeeeong.doodle.domain.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "\"alarm\"", indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE \"alarm\" SET deleted_at = current_timestamp where like_id = ?")
@Where(clause = "deleted_at is NULL")
public class Alarm extends BaseEntityWithUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private AlarmArgs args;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private Alarm(User user, AlarmType alarmType, AlarmArgs args) {
        this.user = user;
        this.alarmType = alarmType;
        this.args = args;
    }

    public static Alarm of(User user, AlarmType alarmType, AlarmArgs args) {
        return new Alarm(user, alarmType, args);
    }
}
