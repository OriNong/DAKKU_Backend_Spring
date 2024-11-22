package kr.re.kh.controller.mypage;

import kr.re.kh.annotation.CurrentUser;
import kr.re.kh.model.CustomUserDetails;
import kr.re.kh.model.payload.response.ApiResponse;
import kr.re.kh.service.UserProfileService;
import kr.re.kh.model.vo.UploadFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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
            // 기존 프로필 이미지 삭제 (이미지가 있을 경우)
            userProfileService.deleteProfileImage(currentUser.getId());

            // 프로필 이미지 업로드 처리
            UploadFile uploadedFile = userProfileService.uploadProfileImage(currentUser.getId(), file);
            // 업로드된 이미지 경로를 응답으로 반환
            return ResponseEntity.ok(uploadedFile.getSaveFileName());
        } catch (Exception e) {
            throw new BadRequestException("파일 저장 오류");
        }
    }

    // 사용자 프로필 이미지 조회
    @GetMapping("/profile-image")
    public ResponseEntity<?> getProfileImage(@CurrentUser CustomUserDetails currentUser) {

        // DB에서 사용자 프로필 이미지 조회
        Optional<UploadFile> uploadFile = userProfileService.getProfileImage(currentUser.getId());

        if (uploadFile.isPresent()) {
            // 프로필 이미지 경로 반환
            return ResponseEntity.ok(new ApiResponse(true, uploadFile.get().getFilePath(), null, "/profile-image"));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "이미지가 없습니다."));
        }



    }
    // 사용자 프로필 이미지 삭제
    @DeleteMapping("/ProfileImage")
    public ResponseEntity<?> deleteProfileImage(@CurrentUser CustomUserDetails currentUser) {
        log.info("Deleting profile image for user: {}", currentUser.getUsername());

        boolean deleted = userProfileService.deleteProfileImage(currentUser.getId());
        return ResponseEntity.ok(new ApiResponse(deleted, deleted ? "프로필 이미지가 삭제되었습니다." : "이미지 삭제 실패", null, "/profile-image"));

    }
}