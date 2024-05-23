package com.seeeeeeong.doodle.domain.alarm.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {

    private Long fromUserId;
    private Long targetId;

}
