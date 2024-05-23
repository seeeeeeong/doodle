package com.seeeeeeong.doodle.domain.alarm.service;

import com.seeeeeeong.doodle.common.exception.BusinessException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.common.repository.EmitterRepository;
import com.seeeeeeong.doodle.domain.alarm.domain.Alarm;
import com.seeeeeeong.doodle.domain.alarm.domain.AlarmArgs;
import com.seeeeeeong.doodle.domain.alarm.domain.AlarmType;
import com.seeeeeeong.doodle.domain.alarm.repository.AlarmRepository;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final static Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final static String ALARM_NAME = "alarm";
    private final EmitterRepository emitterRepository;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    public void send(AlarmType type, AlarmArgs args, Long receiverUserId) {
        User user = userRepository.findById(receiverUserId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Alarm alarm = alarmRepository.save(Alarm.of(user, type, args));

        emitterRepository.get(receiverUserId).ifPresentOrElse(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().id(alarm.toString()).name(ALARM_NAME).data("new alarm"));
            } catch (IOException e) {
                emitterRepository.delete(receiverUserId);
                throw new BusinessException(ErrorCode.ALARM_CONNECT_ERROR);
            }
        }, () -> log.info("No emitter founded"));
    }

    public SseEmitter connectAlarm(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, sseEmitter);
        sseEmitter.onCompletion(() -> emitterRepository.delete(userId));
        sseEmitter.onTimeout(() -> emitterRepository.delete(userId));

        try {
            sseEmitter.send(SseEmitter.event().id("").name(ALARM_NAME).data("connect completed"));
        } catch (IOException exception) {
            throw new BusinessException(ErrorCode.ALARM_CONNECT_ERROR);
        }
        return sseEmitter;
    }
}
