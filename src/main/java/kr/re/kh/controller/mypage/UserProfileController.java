package kr.re.kh.controller.mypage;

import kr.re.kh.annotation.CurrentUser;
import kr.re.kh.model.CustomUserDetails;
import kr.re.kh.model.payload.response.ApiResponse;
import kr.re.kh.service.UserProfileService;
import kr.re.kh.model.vo.UploadFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user-profile")
@Slf4j
@AllArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    // 사용자 프로필 이미지 업로드
    @PostMapping("/ProfileImage")
    public ResponseEntity<?> uploadProfileImage(@CurrentUser CustomUserDetails currentUser,
                                                @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Uploading profile image for user: {}", currentUser.getUsername());
        try {
            // 프로필 이미지 업로드 처리
            UploadFile uploadedFile = userProfileService.uploadProfileImage(currentUser.getId(), file);
            // 업로드된 이미지 경로를 응답으로 반환
            return ResponseEntity.ok(new ApiResponse(true, "프로필 이미지가 업로드되었습니다.", uploadedFile.getFilePath(), "/profile-image"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "프로필 이미지 업로드 실패", e.getMessage(), "/profile-image"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "잘못된 요청입니다.", e.getMessage(), "/profile-image"));
        }
    }

    // 사용자 프로필 이미지 조회
    @GetMapping("/profile-image")
    public ResponseEntity<?> getProfileImage(@CurrentUser CustomUserDetails currentUser) {
        try {
            // DB에서 사용자 프로필 이미지 조회
            UploadFile uploadFile = userProfileService.getProfileImage(currentUser.getId())
                    .orElseThrow(() -> new RuntimeException("프로필 이미지를 찾을 수 없습니다."));

            // 프로필 이미지 경로 반환
            return ResponseEntity.ok(new ApiResponse(true, uploadFile.getFilePath(), null, "/profile-image"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "프로필 이미지 조회 실패", e.getMessage(), "/profile-image"));
        }
    }
    // 사용자 프로필 이미지 삭제
    @DeleteMapping("/ProfileImage")
    public ResponseEntity<?> deleteProfileImage(@CurrentUser CustomUserDetails currentUser) {
        log.info("Deleting profile image for user: {}", currentUser.getUsername());
        try {
            boolean deleted = userProfileService.deleteProfileImage(currentUser.getId());
            if (deleted) {
                return ResponseEntity.ok(new ApiResponse(true, "프로필 이미지가 삭제되었습니다.", null, "/profile-image"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "삭제할 프로필 이미지가 없습니다.", null, "/profile-image"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "프로필 이미지 삭제 실패", e.getMessage(), "/profile-image"));
        }
    }
}