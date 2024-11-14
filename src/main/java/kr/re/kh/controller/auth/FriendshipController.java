package kr.re.kh.controller.auth;

import kr.re.kh.annotation.CurrentUser;
import kr.re.kh.exception.BadRequestException;
import kr.re.kh.model.CustomUserDetails;
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

    @PostMapping("/user/friendRequest/{id}")
    public ResponseEntity<?> sendFriendshipRequest(
            @CurrentUser CustomUserDetails currentUser,
            @PathVariable("id") Long receiverId) {

        // 친구 요청을 보낸 상대가 존재하지 않는 경우의 처리
        if (!userService.existsById(receiverId)) {
            throw new BadRequestException("존재하지 않는 사용자 입니다.");
        }

        // 친구 요청을 보낸 현재 로그인 사용자 id
        Long requesterId = currentUser.getId();

        return ResponseEntity.ok(friendshipService.createFriendship(requesterId, receiverId));
    }
}
