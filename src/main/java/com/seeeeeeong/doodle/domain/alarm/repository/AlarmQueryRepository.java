package com.seeeeeeong.doodle.domain.alarm.repository;

import com.seeeeeeong.doodle.domain.alarm.domain.Alarm;
import com.seeeeeeong.doodle.domain.alarm.dto.AlarmResponse;
import com.seeeeeeong.doodle.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface AlarmQueryRepository {

    Page<AlarmResponse> alarm(User user, Pageable pageable, Sort.Direction direction);

}
