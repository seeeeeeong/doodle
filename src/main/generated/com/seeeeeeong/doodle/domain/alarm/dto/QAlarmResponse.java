package com.seeeeeeong.doodle.domain.alarm.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.seeeeeeong.doodle.domain.alarm.dto.QAlarmResponse is a Querydsl Projection type for AlarmResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAlarmResponse extends ConstructorExpression<AlarmResponse> {

    private static final long serialVersionUID = -1000896315L;

    public QAlarmResponse(com.querydsl.core.types.Expression<Long> alarmId, com.querydsl.core.types.Expression<com.seeeeeeong.doodle.domain.alarm.domain.AlarmType> alarmType, com.querydsl.core.types.Expression<? extends com.seeeeeeong.doodle.domain.alarm.domain.AlarmArgs> args, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> updatedAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> deletedAt) {
        super(AlarmResponse.class, new Class<?>[]{long.class, com.seeeeeeong.doodle.domain.alarm.domain.AlarmType.class, com.seeeeeeong.doodle.domain.alarm.domain.AlarmArgs.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, alarmId, alarmType, args, createdAt, updatedAt, deletedAt);
    }

}

