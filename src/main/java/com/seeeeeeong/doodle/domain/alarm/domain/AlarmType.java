package com.seeeeeeong.doodle.domain.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {

    NEW_COMMENT_ON_POST("new comment"),
    NEW_LIKE_ON_POST("new like"),
    ;

    private final String alarmText;
}
