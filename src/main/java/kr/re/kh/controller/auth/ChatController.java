package kr.re.kh.controller.auth;

import kr.re.kh.annotation.CurrentUser;
import kr.re.kh.model.CustomUserDetails;
import kr.re.kh.model.payload.request.ChatMessageCreateCommand;
import kr.re.kh.model.payload.request.ChatMessageRequest;
import kr.re.kh.service.ChatService;
import kr.re.kh.service.SseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final SseService sseService;

    @MessageMapping("/chat/rooms/{roomId}/send")
    @SendTo("/topic/public/rooms/{roomId}")
    public ResponseEntity<?> sendMessage(@Payload ChatMessageRequest chatMessageRequest) {

        ChatMessageCreateCommand chatMessageCreateCommand = ChatMessageCreateCommand.builder()
                .content(chatMessageRequest.getText())
                .userId(chatMessageRequest.getUserID())
                .friendId(chatMessageRequest.getFriendID())
                .roomID(chatMessageRequest.getRoomId())
                .build();

        if (chatMessageRequest.getUserID().equals(chatMessageRequest.getFriendID())) {
            return ResponseEntity.ok("사용자 본인의 룸을 만들수 없습니다.");
        }

        sseService.sendToUI("채팅 알림", chatMessageRequest.getUserID()); // Sse를 이용해서 사용자에게 채팅 알림이 왔다고 전달.
        chatService.saveMsg(chatMessageCreateCommand);

        ChatMessageRequest chatMessage = ChatMessageRequest.builder()
                .text(chatMessageRequest.getText())
                .userID(chatMessageRequest.getUserID())
                .roomId(chatMessageRequest.getRoomId())
                .friendID(chatMessageRequest.getFriendID())
                .build();

        return ResponseEntity.ok(chatMessage);
    }

    @GetMapping("/chat/uuid")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    public ResponseEntity<?> generateUUID(
            @CurrentUser CustomUserDetails currentUser,
            @RequestParam("friendID") Long friendID
    ) {
        return ResponseEntity.ok(chatService.checkRoom(currentUser, friendID));
    }

    @GetMapping("/chat/userRoom")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    public ResponseEntity<?> generateRoom(
            @CurrentUser CustomUserDetails currentUser
    ) {
        log.info(currentUser.toString());
        return ResponseEntity.ok(chatService.userRoomCount(currentUser.getId()));
    }

}
