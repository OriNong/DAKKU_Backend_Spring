package kr.re.kh.controller.auth;

import kr.re.kh.annotation.CurrentUser;
import kr.re.kh.model.CustomUserDetails;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ChatController {

    private ChatService chatService;

    @MessageMapping("/chat/rooms/{roomId}/send")
    @SendTo("/topic/public/rooms/{roomId}")
    public ResponseEntity<?> sendMessage(@Payload ChatMessageRequest chatMessageRequest) {
        log.info(chatMessageRequest.toString());
        ChatMessageCreateCommand chatMessageCreateCommand = ChatMessageCreateCommand.builder()
                .content(chatMessageRequest.getText())
                .userId(chatMessageRequest.getUserID())
                .friendId(chatMessageRequest.getFriendID())
                .build();

        return ResponseEntity.ok(chatService.chat(chatMessageCreateCommand));
    }

    @GetMapping("/api/chat/uuid")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    public ResponseEntity<?> generateUUID(
            @CurrentUser CustomUserDetails currentUser,
            @RequestParam("friendID") Long friendID
    ) {
        Long myId = currentUser.getId();
        log.info(myId.toString());

        return ResponseEntity.ok(chatService.checkRoom(myId, friendID));
    }

}
