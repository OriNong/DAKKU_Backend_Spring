package kr.re.kh.service;

import kr.re.kh.mapper.DiaryMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;

    // 로그인 사용자 자신의 일기 조회

    // 개별 사용자의 공개로 게시된 일기 조회

    // 공개로 게시된 전체 일기 조회

    // 일기 저장

    // 일기 수정 시 해당 일기 조회 후 반환

    // 일기 수정

    // 일기 삭제
}
