package com.metain.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseEmitters {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SseEmitter add(String userId, SseEmitter emitter) {
        this.emitters.put(userId, emitter);
        logger.info("new emitter added: {}",emitter);
        logger.info("emitter list size: {}",emitters.size());

        emitter.onCompletion(() -> {
            this.emitters.remove(emitter);    // 만료되면 리스트에서 삭제
        });

        emitter.onTimeout(() -> {
            logger.info("EMITTER 시간초과");
            emitter.complete();
        });

        return emitter;
    }
}
