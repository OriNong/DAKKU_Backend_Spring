package kr.re.kh.mapper;

import kr.re.kh.model.vo.UploadFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UploadFileMapper {

    Optional<UploadFile> selectFileById(Long id);

    Optional<UploadFile> selectFileByIdAndFileTarget(UploadFile uploadFile);

    Optional<UploadFile> selectFileAsSaveFileName(String saveFileName);

    void insertFile(UploadFile uploadFile);

    void deleteByFileByIdAndFileTarget(UploadFile uploadFile);

    List<UploadFile> selectFileByBoardId(Long id);

    // 프로필 사진 조회 (사용자 ID로 프로필 사진을 찾기)
    Optional<UploadFile> selectProfileImageByUserId(Long userId);

    // 프로필 사진 저장
    void insertProfileImage(UploadFile uploadFile);

    // 프로필 사진 삭제
    void deleteProfileImage(Long fileId);

}
