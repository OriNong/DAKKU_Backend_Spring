package kr.re.kh.controller.auth;

import kr.re.kh.model.payload.response.ApiResponse;
import kr.re.kh.service.SseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/message")
@Slf4j
@AllArgsConstructor
public class SseController {
    private SseService sseService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        // 얼마나 살아있을지 정한다. : Long.MAX_VALUE
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        log.info("등록된 Sse 주소 : " + sseEmitter.toString());

        sseService.register(sseEmitter);

        return sseEmitter;
    }

    /**
     * Post로 보내기 위한 Controller
     * @param msg
     * @param userID
     * @return
     */
    @GetMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(
            @RequestParam(value = "msg") String msg, @RequestParam("userID") Long userID
    ) {
        sseService.sendPostToUI(msg, userID);

        return ResponseEntity.ok(new ApiResponse(true, "전송"));
    }
}
