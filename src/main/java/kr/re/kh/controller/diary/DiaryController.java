package kr.re.kh.controller.diary;

import kr.re.kh.model.payload.response.ApiResponse;
import kr.re.kh.model.vo.DiaryVO;
import kr.re.kh.service.DiaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diary")
@Slf4j
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    /**
     * 로그인한 사용자의 일기 목록
     * @param memberId : 로그인한 사용자의 memberID
     * @return : 로그인한 사용자의 일기 목록
     */
    @GetMapping("/mypage/{memberId}")
    public ResponseEntity<List<DiaryVO>> showMyDiary(@PathVariable("memberId") Long memberId) {
        List<DiaryVO> myDiaries = diaryService.selectAllDiaryByMemberId(memberId);
        return ResponseEntity.ok(myDiaries);
    }

    /**
     * 특정 사용자의 공개로 게시된 일기 조회
     * @param memberId : 특정 사용자의 memberId
     * @return : 특정 사용자의 일기 목록
     */
    @GetMapping("/members/{memberId}")
    public ResponseEntity<List<DiaryVO>> showMemberDiary(@PathVariable("memberId") Long memberId) {
        List<DiaryVO> memberPublicDiaries = diaryService.selectAllPublicDiaryByMemberId(memberId);
        return ResponseEntity.ok(memberPublicDiaries);
    }

    /**
     * 전체 사용자의 공개로 게시된 일기 조회
     * 좋아요 순 오름차순 -> 조회 수 오름차순
     * @return
     */
    @GetMapping("/public")
    public ResponseEntity<List<DiaryVO>> showPublicDiary() {
        List<DiaryVO> publicDiaries = diaryService.selectAllPublicDiary();
        return ResponseEntity.ok(publicDiaries);
    }

    /**
     * 일기 선택 시 해당 일기 조회
     * @param diaryId : 일기 고유 id
     * @return : 선택된 일기
     */
    @GetMapping("/{diaryId}}")
    public ResponseEntity<DiaryVO> showSelectedDiary(@PathVariable Long diaryId) {
        DiaryVO diary = diaryService.selectDiaryByDiaryId(diaryId);
        return ResponseEntity.ok(diary);
    }

    /**
     * 일기 저장
     * @param diary : 클라이언트에서 diaryVO와 필드명이 일치하는 Json 데이터 수신
     * @return : 저장 완료
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveDiary(@RequestBody DiaryVO diary) {
        diaryService.saveDiary(diary);
        return ResponseEntity.ok(new ApiResponse(true, "저장되었습니다."));
    }

    /**
     * 일기 수정
     * @param diaryId : 일기 고유 id
     * @param diary : 수정된 일기 내용
     * @return : 수정 완료
     */
    @PutMapping("/update/{diaryId}")
    public ResponseEntity<?> updateDiary(@PathVariable Long diaryId, @RequestBody DiaryVO diary) {
        if (diaryService.selectDiaryByDiaryId(diaryId) == null) {
            return ResponseEntity.notFound().build();
        }
        diary.setDiaryId(diaryId);
        diaryService.updateDiary(diary);
        return ResponseEntity.ok().build();
    }


}
