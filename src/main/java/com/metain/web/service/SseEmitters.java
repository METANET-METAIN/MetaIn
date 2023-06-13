package com.metain.web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseEmitters {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter add(String userId, SseEmitter emitter) {
        this.emitters.put(userId, emitter);
        System.out.println("new emitter added: {}"+emitter);
        System.out.println("emitter list size: {}"+emitters.size());

        emitter.onCompletion(() -> {
            System.out.println("onCompletion callback");
            this.emitters.remove(emitter);    // 만료되면 리스트에서 삭제
        });

        emitter.onTimeout(() -> {
            System.out.println("onTimeout callback");
            emitter.complete();
        });

        return emitter;
    }
}
