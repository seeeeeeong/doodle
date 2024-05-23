package com.seeeeeeong.doodle.common.producer;

import com.seeeeeeong.doodle.domain.alarm.domain.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmProducer {

    private final KafkaTemplate<Long, AlarmEvent> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    public void send(AlarmEvent event) {
        kafkaTemplate.send(topic, event.getReceverUserId(), event);
        log.info("send to kafka finished");
    }
}
