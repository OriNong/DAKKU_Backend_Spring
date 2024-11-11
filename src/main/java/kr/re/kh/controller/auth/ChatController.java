package kr.re.kh.controller.auth;

import kr.re.kh.model.payload.request.ChatMessageCreateCommand;
import kr.re.kh.model.payload.request.ChatMessageRequest;
import kr.re.kh.model.payload.response.ChatMessageResponse;
import kr.re.kh.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
@AllArgsConstructor
public class ChatController {

    private ChatService chatService;

    @MessageMapping("/chat/rooms/{roomId}/send")
    @SendTo("/topic/public/rooms/{roomId}")
    public ResponseEntity<?> sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageRequest chatMessageRequest) {
        log.info(roomId.toString());
        log.info(chatMessageRequest.toString());

        // 생각을 해보니까 roomId는 숫자를 랜덤으로 설정해서 넣어주어야 될것 같다 안그러면 다른 사람들이 대화방에 침입 가능성이 있음.


        ChatMessageCreateCommand chatMessageCreateCommand = ChatMessageCreateCommand.builder()
                .content(chatMessageRequest.getText())
                .from(chatMessageRequest.getFrom())
                .userId(6L) // userID는 백엔드 내부에서 가져오는 걸로 로직 구성 준비중...
                .roomId(roomId)
                .build();
        chatService.chat(chatMessageCreateCommand);
//        Long chatId = chatMessageCreateCommand.getRoomId();
        // 위 chatId 대신 아래의 서비스 로직 구현
        // chatService.chat(chatMessageCreateCommand);
        // 1. 방 번호가 없으면 방을 만들고 방금 받은 메시지를  db에 넣고
        // 1번의 구조는 방테이블, 메시지 테이블 필요
        // 2. 생성된 방에 메시지 목록을 쿼리해서 리턴
        List<ChatMessageResponse> messageResponseList = new ArrayList<>();

        // messageResponseList = chatService.chat(chatMessageCreateCommand);

        return ResponseEntity.ok(messageResponseList);
    }

}
