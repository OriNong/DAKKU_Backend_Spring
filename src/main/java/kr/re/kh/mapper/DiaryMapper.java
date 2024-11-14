package kr.re.kh.mapper;

import kr.re.kh.model.vo.DiaryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {
    // 로그인 사용자 자신의 일기 조회
    List<DiaryVO> selectAllDiaryByMemberId(Long memberId);

    // 개별 사용자의 공개로 게시된 일기 조회
    List<DiaryVO> selectPublicDiaryByMemberId(Long memberId);

    // 공개로 게시된 일기 조회
    List<DiaryVO> selectAllPublicDiary();

    // 일기 저장
    void saveDiary(DiaryVO diary);

    // 일기 수정 시 해당 일기 조회 후 반환
    DiaryVO selectDiaryWhenUptDiary(Long diaryId);

    // 일기 수정
    void updateDiary(DiaryVO diary);

    // 일기 삭제
    void deleteDiary(Long diaryId);
}
