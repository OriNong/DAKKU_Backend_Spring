package kr.re.kh.service;

import kr.re.kh.mapper.DiaryMapper;
import kr.re.kh.model.vo.DiaryVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;

    // 로그인 사용자 자신의 일기 조회
    public List<DiaryVO> selectAllDiaryByMemberId(Long memberId) {
        return diaryMapper.selectAllDiaryByMemberId(memberId);
    }

    // 개별 사용자의 공개로 게시된 일기 조회
    public List<DiaryVO> selectAllPublicDiaryByMemberId(Long memberId) {
        return diaryMapper.selectPublicDiaryByMemberId(memberId);
    }

    // 공개로 게시된 전체 일기 조회
    public List<DiaryVO> selectAllPublicDiary() {
        return diaryMapper.selectAllPublicDiary();
    }
    // 일기 저장
    public void saveDiary(DiaryVO diary) {
        diaryMapper.saveDiary(diary);
    }
    // 일기 고유 id로 일기 조회
    public DiaryVO selectDiaryByDiaryId(Long diaryId) {
        return diaryMapper.selectDiaryWhenUptDiary(diaryId);
    }
    // 일기 수정
    public void updateDiary(DiaryVO diary) {
        diaryMapper.updateDiary(diary);
    }
    // 일기 삭제
    public void deleteDiary(Long diaryId) {
        diaryMapper.deleteDiary(diaryId);
    }
}
