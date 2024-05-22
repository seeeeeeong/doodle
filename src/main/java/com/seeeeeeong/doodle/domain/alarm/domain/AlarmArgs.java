package com.seeeeeeong.doodle.domain.alarm.domain;

import lombok.*;

@Data
@AllArgsConstructor
public class AlarmArgs {

    private Long fromUserId;
    private Long targetId;

}
