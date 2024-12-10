package kr.re.kh.service;

import kr.re.kh.mapper.SseMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
@AllArgsConstructor
public class SseService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final SseMapper sseMapper;

    public void register(SseEmitter sseEmitter) {
        sseEmitter.onTimeout(() -> timeout(sseEmitter));
        sseEmitter.onCompletion(() -> timeout(sseEmitter));

        emitters.add(sseEmitter);
    }

    public void complete(SseEmitter sseEmitter) {
        log.info("emitter Complete");
        emitters.remove(sseEmitter);
    }

    public void timeout(SseEmitter sseEmitter) {
        log.info("emitter timeout");
        emitters.remove(sseEmitter);
    }

    /**
     * 채팅에서 보내는 알림 Service
     * @param message
     * @param userID
     * @param roomId
     */
    public void sendToUI(String message, Long userID, String roomId) {
        log.info("서버에서 보내는 메시지 : " + message);
        HashMap<String, Object> result = new HashMap<>();
        result.put("message", message);
        result.put("userID", userID);
        result.put("roomId", roomId);

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().reconnectTime(500).data(result));
            } catch (Exception e) {
                emitter.complete();
            }
        }
    }

    /**
     * 전송 테스트를 위한 Service
     * @param message
     * @param userID
     */
    public void sendPostToUI(String message, Long userID) {
        log.info("서버에서 보내는 메시지 : " + message);
        HashMap<String, Object> result = new HashMap<>();
        result.put("message", message);
        result.put("userID", userID);

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().reconnectTime(500).data(result));
            } catch (Exception e) {
                emitter.complete();
            }
        }
    }

}
