package com.seeeeeeong.doodle.domain.alarm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.querydsl.core.annotations.QueryProjection;
import com.seeeeeeong.doodle.domain.alarm.domain.AlarmArgs;
import com.seeeeeeong.doodle.domain.alarm.domain.AlarmType;
import com.seeeeeeong.doodle.domain.post.dto.PostResponse;
import com.seeeeeeong.doodle.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AlarmResponse {

    private Long alarmId;
    private AlarmType alarmType;
    private AlarmArgs args;
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime deletedAt;

    @QueryProjection
    public AlarmResponse(Long alarmId, AlarmType alarmType, AlarmArgs args, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.alarmId = alarmId;
        this.alarmType = alarmType;
        this.args = args;
        this.text = alarmType.getAlarmText();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static class Page extends PageImpl<AlarmResponse> {
        public Page(List<AlarmResponse> content,
                    Pageable pageable, long total) {
            super(content, pageable, total);
        }

    }
}
