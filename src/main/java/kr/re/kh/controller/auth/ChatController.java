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

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/api")
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;

    /**
     * 유저가 특정 roomId로 들어가면 서버와 연결, 그후 채팅을 날릴시 채팅을 전송.
     *
     * @param chatMessageRequest
     * @return
     */
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

        chatService.saveMsg(chatMessageCreateCommand);

        // 현 프로젝트 틀.
        HashMap<String, Object> result = new HashMap<>();
        result.put("text", chatMessageRequest.getText());
        result.put("title", chatMessageRequest.getFriendName());
        result.put("roomId", chatMessageRequest.getRoomId());
        result.put("userName", chatMessageRequest.getUserName());
        result.put("friendID", chatMessageRequest.getFriendID());
        result.put("userID", chatMessageRequest.getUserID());

        return ResponseEntity.ok(result);
    }

    /**
     * 유저의 채팅방을 불러온다. 없으면 새로 만들기
     *
     * @param currentUser
     * @param friendID
     * @return
     */
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
        return ResponseEntity.ok(chatService.userRoomCount(currentUser.getId()));
    }

    @GetMapping("/chat/friendList")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    public ResponseEntity<?> chatFriendList(@CurrentUser CustomUserDetails customUser) {
        return ResponseEntity.ok(chatService.getFriendList(customUser.getId()));
    }

}
