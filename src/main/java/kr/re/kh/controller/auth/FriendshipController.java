package kr.re.kh.controller.auth;

import kr.re.kh.model.payload.response.ApiResponse;
import kr.re.kh.service.FriendshipService;
import kr.re.kh.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social")
@Slf4j
@AllArgsConstructor
public class FriendshipController {
    private FriendshipService friendshipService;
    private UserService userService;

    @PostMapping("/user/friends/{id}")
    public ResponseEntity<?> sendFriendshipRequest(@PathVariable("id") long friendId) {
        if (!userService.existsById(friendId)) {
            return ResponseEntity.notFound().build();
        }
        friendshipService.createFriendship(friendId);
        return ResponseEntity.ok(new ApiResponse(true, "친구 요청 완료"));
    }
}
