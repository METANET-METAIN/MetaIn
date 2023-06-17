package com.metain.web.service;

import com.metain.web.dto.AlarmResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {
    public void send(Long userId, AlarmResponse response);
    public SseEmitter connectAlarm(Long userId);
}
