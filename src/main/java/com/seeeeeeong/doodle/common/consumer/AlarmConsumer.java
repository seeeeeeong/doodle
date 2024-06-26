package com.seeeeeeong.doodle.common.consumer;

import com.seeeeeeong.doodle.domain.alarm.domain.AlarmEvent;
import com.seeeeeeong.doodle.domain.alarm.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    private final AlarmService alarmService;

    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
    public void consumeAlarm(AlarmEvent event, Acknowledgment ack) {
        log.info("consume the event {}", event);
        alarmService.send(event.getType(), event.getArgs(), event.getReceverUserId());
        ack.acknowledge();
    }
}
