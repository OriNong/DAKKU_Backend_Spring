package kr.re.kh.controller.mypage;

import kr.re.kh.annotation.CurrentUser;
import kr.re.kh.model.CustomUserDetails;
import kr.re.kh.model.payload.response.ApiResponse;
import kr.re.kh.service.UserProfileService;
import kr.re.kh.model.vo.UploadFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @PostMapping("/profile-image")
    public ResponseEntity<?> uploadProfileImage(@CurrentUser CustomUserDetails currentUser,
                                                @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Uploading profile image for user: {}", currentUser.getUsername());
        try {
            UploadFile uploadedFile = userProfileService.uploadProfileImage(currentUser.getId(), file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new ApiResponse(true, "프로필 이미지가 업로드되었습니다."));
    }

    // 사용자 프로필 이미지 삭제
    @DeleteMapping("/profile-image")
    public ResponseEntity<?> deleteProfileImage(@CurrentUser CustomUserDetails currentUser) {
        log.info("Deleting profile image for user: {}", currentUser.getUsername());
        boolean deleted = userProfileService.deleteProfileImage(currentUser.getId());
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse(true, "프로필 이미지가 삭제되었습니다."));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "삭제할 프로필 이미지가 없습니다."));
        }
    }
}