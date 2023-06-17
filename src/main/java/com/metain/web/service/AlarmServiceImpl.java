package com.metain.web.service;

import com.metain.web.dto.AlarmResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class AlarmServiceImpl implements AlarmService {
    private final static Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final static String ALARM_NAME = "alarm";

    @Resource
    private EmitterRepository  EmitterRepository ;

    public void send(Long userId, AlarmResponse response) {
        SseEmitter sseEmitter = EmitterRepository.get(userId).orElse(null);
        if (sseEmitter != null) {
            try {
                sseEmitter.send(SseEmitter.event().name(ALARM_NAME).data(response));
            } catch (IOException e) {
                EmitterRepository.delete(userId);
                throw new IllegalStateException("");
            }
        } else {
            System.out.println("No emitter found");
        }
    }

    public SseEmitter connectAlarm(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        EmitterRepository.save(userId, sseEmitter);

        sseEmitter.onCompletion(() -> EmitterRepository.delete(userId));
        sseEmitter.onTimeout(() -> EmitterRepository.delete(userId));

        try {
            sseEmitter.send(SseEmitter.event().id("").name("subscribe").data(AlarmResponse.comment("connected")));
        } catch (IOException exception) {
            throw new IllegalStateException("");
        }

        return sseEmitter;
    }
}
