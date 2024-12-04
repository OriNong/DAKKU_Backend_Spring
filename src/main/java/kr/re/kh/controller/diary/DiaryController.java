package kr.re.kh.controller.diary;

import kr.re.kh.annotation.CurrentUser;
import kr.re.kh.model.CustomUserDetails;
import kr.re.kh.model.User;
import kr.re.kh.model.payload.response.ApiResponse;
import kr.re.kh.model.vo.DiaryVO;
import kr.re.kh.service.DiaryService;
import kr.re.kh.service.WeatherIconService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diary")
@Slf4j
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private WeatherIconService weatherService;

    /**
     * 로그인한 사용자의 일기 목록을 가장 최근에 작성한 일기부터 조회
     * @param currentUser : 로그인 사용자
     * @return : List<DiaryVO> myDiaries
     */
    @GetMapping("/myDiaries")
    public ResponseEntity<List<DiaryVO>> showMyDiary(@CurrentUser CustomUserDetails currentUser) {
        Long memberId = currentUser.getId();
        List<DiaryVO> myDiaries = diaryService.selectAllDiaryByMemberId(memberId);
        int i = 1;
        for (DiaryVO diaryVO : myDiaries) {
            log.info(i + "번째 일기 정보");
            log.info(diaryVO.toString());
            log.info("=======================");
            i++;
        }
        return ResponseEntity.ok(myDiaries);
    }

    // 일기 isPublic Update 로직 작성

    /**
     * 특정 사용자의 공개로 게시된 일기 조회
     * @param memberId : 특정 사용자의 memberId
     * @return : 특정 사용자의 일기 목록
     */
    @GetMapping("/memberDiaries/{memberId}")
    public ResponseEntity<List<DiaryVO>> showMemberDiary(@PathVariable("memberId") Long memberId) {
        List<DiaryVO> memberPublicDiaries = diaryService.selectAllPublicDiaryByMemberId(memberId);
        return ResponseEntity.ok(memberPublicDiaries);
    }

    /**
     * 로그인 사용자의 일기를 특정 날짜를 기준으로 조회
     * @param currentUser : 로그인 사용자
     * @param selectedDate : 선택된 날짜 (yyyy-MM-dd 형식의 문자열)
     * @return
     */
    @GetMapping("/myDiaries/date/{selectedDate}")
    public ResponseEntity<List<DiaryVO>> showMyDiaryByDate(@CurrentUser CustomUserDetails currentUser ,@PathVariable("selectedDate") String selectedDate) {
        Long memberId = currentUser.getId();

        // List null 체크를 하지않고 클라이언트로 전달 -> 클라이언트에서 null에 대한 처리 수행
        return ResponseEntity.ok(diaryService.selectUserDiaryByDate(memberId, selectedDate));
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
    @GetMapping("/select/{diaryId}")
    public ResponseEntity<DiaryVO> showSelectedDiary(@PathVariable("diaryId") Long diaryId) {
        DiaryVO diary = diaryService.selectDiaryByDiaryId(diaryId);
        return ResponseEntity.ok(diary);
    }

    /**
     * 일기 저장
     * @param diary : 클라이언트에서 diaryVO와 필드명이 일치하는 Json 데이터 수신
     * @return : 저장 완료
     */
    @PostMapping("/save")
    @PreAuthorize("hasRole('USER') or hasRole('SYSTEM') or hasRole('ADMIN')") // user만 일기 저장할 수 있게 한다.
    public ResponseEntity<?> saveDiary(@RequestBody DiaryVO diary, @CurrentUser CustomUserDetails currentUser) {
        try {
            // 일기 저장
            log.info(diary.toString());
            diary.setMemberId(currentUser.getId());
            diaryService.saveDiary(diary);
            return ResponseEntity.ok(new ApiResponse(true, "저장되었습니다."));
        } catch (Exception e) {
            e.printStackTrace(); // 서버 로그에 에러 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "일기 저장 중 오류 발생"));
        }
    }
    /**
     * 일기 수정
     * @param diaryId : 일기 고유 id
     * @param diary : 수정된 일기 내용
     * @return : 수정 완료
     */
    @PutMapping("/update/{diaryId}")
    public ResponseEntity<?> updateDiary(@PathVariable("diaryId") Long diaryId, @RequestBody DiaryVO diary) {
        if (diaryService.selectDiaryByDiaryId(diaryId) == null) {
            return ResponseEntity.notFound().build();
        }
        log.info(diary.toString());
        diary.setDiaryId(diaryId);
        diaryService.updateDiary(diary);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable("diaryId") Long diaryId) {
        try {
            diaryService.deleteDiary(diaryId);
            return ResponseEntity.ok(new ApiResponse(true, "일기 삭제 성공"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "일기 삭제 중 오류 발생"));
        }
    }


}
