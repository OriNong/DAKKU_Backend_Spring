package kr.re.kh.service;

import kr.re.kh.exception.BadRequestException;
import kr.re.kh.mapper.UploadFileMapper;
import kr.re.kh.model.vo.UploadFile;
import kr.re.kh.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UploadFileMapper uploadFileMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;  // application.yml에서 경로 설정

    // 프로필 이미지 업로드
    public UploadFile uploadProfileImage(Long userId, MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new BadRequestException("파일이 비어 있습니다.");
        }

        // 기존 프로필 이미지가 있으면 삭제
        Optional<UploadFile> existingFileOpt = uploadFileMapper.selectProfileImageByUserId(userId);
        if (existingFileOpt.isPresent()) {
            UploadFile existingFile = existingFileOpt.get();
            // 로컬 파일 삭제
            boolean fileDeleted = UploadFileUtil.deleteFile(existingFile.getFilePath());
            if (!fileDeleted) {
                throw new IOException("기존 파일 삭제 실패.");
            }
            // DB에서 기존 프로필 이미지 삭제
            uploadFileMapper.deleteProfileImage(existingFile.getId());
        }

        // 파일 저장
        String saveFileName = UploadFileUtil.fileSave(uploadDir, file);  // 파일을 로컬 디렉토리에 저장

        // DB에 저장할 파일 정보 생성
        UploadFile uploadFile = new UploadFile();
        uploadFile.setUsername(String.valueOf(userId));  // 사용자 ID
        uploadFile.setFileName(file.getOriginalFilename());
        uploadFile.setSaveFileName(saveFileName);
        uploadFile.setFilePath(uploadDir + "/" + saveFileName);  // 저장된 경로
        uploadFile.setContentType(file.getContentType());
        uploadFile.setFileSize(file.getSize());
        uploadFile.setFileTarget("profile");  // 'profile'로 설정하여 프로필 사진 구별

        // DB에 파일 정보 저장
        uploadFileMapper.insertProfileImage(uploadFile);

        return uploadFile;
    }

    // 프로필 이미지 삭제
    public boolean deleteProfileImage(Long userId) {
        // 프로필 이미지 조회
        Optional<UploadFile> existingFileOpt = uploadFileMapper.selectProfileImageByUserId(userId);
        if (existingFileOpt.isPresent()) {
            UploadFile existingFile = existingFileOpt.get();
            // 로컬 파일 삭제
            boolean fileDeleted = UploadFileUtil.deleteFile(existingFile.getFilePath());
            if (fileDeleted) {
                // DB에서 삭제
                uploadFileMapper.deleteProfileImage(existingFile.getId());
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    // 프로필 이미지 조회
    public Optional<UploadFile> getProfileImage(Long userId) {
        return uploadFileMapper.selectProfileImageByUserId(userId);
    }
}