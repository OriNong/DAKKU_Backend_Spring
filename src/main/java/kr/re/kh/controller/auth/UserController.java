package kr.re.kh.controller.auth;

import kr.re.kh.annotation.CurrentUser;
import kr.re.kh.event.OnUserLogoutSuccessEvent;
import kr.re.kh.model.CustomUserDetails;
import kr.re.kh.model.User;
import kr.re.kh.model.payload.request.LogOutRequest;
import kr.re.kh.model.payload.response.ApiResponse;
import kr.re.kh.model.payload.response.UserResponse;
import kr.re.kh.model.vo.UserProfile;
import kr.re.kh.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 현재 사용자의 프로필 리턴
     * @param currentUser
     * @return
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    public ResponseEntity<?> getUserProfile(@CurrentUser CustomUserDetails currentUser) {
        log.info(currentUser.getEmail() + " has role: " + currentUser.getRoles() + " username: " + currentUser.getUsername());
        UserResponse userResponse = new UserResponse(currentUser.getUsername(), currentUser.getEmail(), currentUser.getRoles(), currentUser.getId(), currentUser.getName());
        return ResponseEntity.ok(userResponse);
    }

    /**
     * 로그아웃
     * @param customUserDetails
     * @param logOutRequest
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@CurrentUser CustomUserDetails customUserDetails,
                                        @Valid @RequestBody LogOutRequest logOutRequest) {
        log.info(customUserDetails.toString());
        log.info(logOutRequest.toString());
        userService.logoutUser(customUserDetails, logOutRequest);
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();

        OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(customUserDetails.getEmail(), credentials.toString(), logOutRequest);
        applicationEventPublisher.publishEvent(logoutSuccessEvent);
        return ResponseEntity.ok(new ApiResponse(true, "로그아웃 되었습니다."));
    }

    /**
     * Password 임시발급하여 바꾸기
     * @param requestMap
     * @return
     */
    @PostMapping("/changePW")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> changePW(@RequestBody HashMap<String, String> requestMap) {
        // 요청받은 HashMap에서 이메일과 사용자 아이디 추출
        String username = requestMap.get("username");
        String email = requestMap.get("email");
        // 비밀번호 찾기 및 임시 비밀번호 발급 로직
        Map<String, Object> response = userService.changePW(username, email); // userService로 결과 받기
        // 결과를 ResponseEntity로 반환
        return ResponseEntity.ok(response);
    }

    /**
     * 전체 사용자 이미지와 함께 조회
     * @return
     */
    @GetMapping("/profiles")
    public ResponseEntity<List<UserProfile>> getUserProfiles(){
        return ResponseEntity.ok(userService.getUserProfiles());
    }
    // 특정 사용자 정보 조회
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username)); //
    }

}
