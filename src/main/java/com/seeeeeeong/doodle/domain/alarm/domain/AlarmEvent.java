package com.seeeeeeong.doodle.domain.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmEvent {

    private Long receverUserId;
    private AlarmType type;
    private AlarmArgs args;

}
