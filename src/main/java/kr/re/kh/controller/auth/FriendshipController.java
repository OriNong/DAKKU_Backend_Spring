package kr.re.kh.controller.auth;

import kr.re.kh.annotation.CurrentUser;
import kr.re.kh.exception.BadRequestException;
import kr.re.kh.model.CustomUserDetails;
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

    /**
     * 친구 요청 전송
     * @param currentUser : 현재 로그인 한 사용자                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
     */
    @PostMapping("/user/friendRequest/{id}")
    public ResponseEntity<?> sendFriendshipRequest(
            @CurrentUser CustomUserDetails currentUser,
            @PathVariable("id") Long receiverId) {

        // 친구 요청을 보낸 상대가 존재하지 않는 경우의 처리
        if (!userService.existsById(receiverId)) {
            throw new BadRequestException("친구 요청 대상 사용자가 존재하지 않습니다.");
        }

        // 친구 요청을 보낸 현재 로그인 사용자 id
        Long requesterId = currentUser.getId();
        log.info("============================================================");
        log.info("로그인 사용자 : " + requesterId.toString());
        log.info("친구 요청 대상 : " + receiverId.toString());
        log.info("============================================================");
        return ResponseEntity.ok(friendshipService.createFriendship(requesterId, receiverId));
    }

    /**
     * 받은 친구요청 조회
     * @param currentUser : 현재 로그인 사용자
     * @return
     */
    @GetMapping("/user/friends/received")
    public ResponseEntity<?> getWaitingFriendInfo(@CurrentUser CustomUserDetails currentUser) {
        Long receivedId = currentUser.getId();
        return friendshipService.getWaitingFriendList(receivedId);
    }

    /**
     * 받은 친구 요청 수락
     * @param friendshipId : 자신이 수신한 친구 요청 관계 friendshipVO의 고유 id
     * @return :
     */
    @PostMapping("/user/friends/received/accept/{friendshipId}")
    public ResponseEntity<?> acceptFriendshipRequest(@PathVariable("friendshipId") Long friendshipId){
    return friendshipService.acceptFriendshipRequest(friendshipId);
    }

    /**
     * 받은 친구 요청 거절
     * @param friendshipId : 자신이 수신한 친구 요청 관계 friendshipVO의 고유 id
     * @return :
     */
    @PostMapping("/user/friends/received/reject/{friendshipId}")
    public ResponseEntity<?> rejectFriendshipRequest(@PathVariable("friendshipId") Long friendshipId){
        return friendshipService.rejectFriendshipRequest(friendshipId);
    }
}
