package kr.re.kh.service;

import kr.re.kh.exception.BadRequestException;
import kr.re.kh.mapper.DiaryMapper;
import kr.re.kh.model.vo.DiaryVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 로그인 사용자의 일기를 특정 날짜를 기준으로 조회
    public List<DiaryVO> selectUserDiaryByDate(Long memberId, String selectedDate) {
        Map<String, Object> userIdAndDate = new HashMap<>();
        userIdAndDate.put("memberId", memberId);
        userIdAndDate.put("selectedDate", selectedDate);
        List<DiaryVO> diariesInSelectedDate = diaryMapper.selectUserDiaryByDate(userIdAndDate);

        return diariesInSelectedDate;
    }

    // 공개로 게시된 전체 일기 조회
    public List<DiaryVO> selectAllPublicDiary() {
        return diaryMapper.selectAllPublicDiary();
    }

    // 일기 저장
    public void saveDiary(DiaryVO diary) {
        log.info("diary: {}", diary);
        try {
            diaryMapper.saveDiary(diary); // DB 저장
        } catch (Exception e) {
            throw new BadRequestException("일기 저장 실패", e); // 예외 처리
        }
    }

    // 일기 고유 id로 일기 조회
    public DiaryVO selectDiaryByDiaryId(Long diaryId) {
        return diaryMapper.selectDiaryWhenUptDiary(diaryId);
    }

    // 일기 수정
    public void updateDiary(DiaryVO diary) {
        try {
            diaryMapper.updateDiary(diary);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BadRequestException("선택된 일기 조회 실패");
        }

    }

    // 일기의 공개 / 비공개 상태 변경
    public void updateDiaryPublic(Long diaryId, Boolean isPublic) {
        Map<String, Object> diaryIdWithPublic = new HashMap<>();
        diaryIdWithPublic.put("diaryId", diaryId);
        diaryIdWithPublic.put("isPublic", isPublic);
        try{
            diaryMapper.updateDiaryPublic(diaryIdWithPublic);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BadRequestException("일기 공개 범위 수정 중 오류 발생");
        }
    }

    // 일기 삭제
    public void deleteDiary(Long diaryId) {
        diaryMapper.deleteDiaryById(diaryId);
    }
}
