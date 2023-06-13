package com.metain.web.controller;

import com.metain.web.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class AlarmController {
    @Autowired
    private AlarmService alarmService;
    @GetMapping("/alarm/subscribe")
    public SseEmitter subscribe(@RequestParam Long userId) {
        return alarmService.connectAlarm(userId);
    }
}
