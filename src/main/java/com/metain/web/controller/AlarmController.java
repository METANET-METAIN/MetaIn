package com.metain.web.controller;

import com.metain.web.domain.PrincipalDetails;
import com.metain.web.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public SseEmitter subscribe(Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        Long userId=principalDetails.getEmpId();
        System.out.println(userId);
        return alarmService.connectAlarm(userId);
    }
}
