package com.seeeeeeong.doodle.domain.alarm.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seeeeeeong.doodle.domain.alarm.dto.AlarmResponse;
import com.seeeeeeong.doodle.domain.alarm.dto.QAlarmResponse;
import com.seeeeeeong.doodle.domain.user.domain.QUser;
import com.seeeeeeong.doodle.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.seeeeeeong.doodle.domain.alarm.domain.QAlarm.alarm;
import static com.seeeeeeong.doodle.domain.comment.domain.QComment.comment1;

@RequiredArgsConstructor
public class AlarmQueryRepositoryImpl implements AlarmQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AlarmResponse> alarm(User user, Pageable pageable, Sort.Direction direction) {

        List<AlarmResponse> content = queryFactory.select(
                new QAlarmResponse(alarm.alarmId, alarm.alarmType, alarm.args, alarm.createdAt, alarm.updatedAt, alarm.deletedAt))
                .from(alarm)
                .where(alarm.user.userId.eq(user.getUserId()))
                .orderBy(direction.isAscending() ? alarm.createdAt.asc() : alarm.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(alarm.count()).from(alarm);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
