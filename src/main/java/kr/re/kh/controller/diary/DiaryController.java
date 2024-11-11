package kr.re.kh.controller.diary;

import kr.re.kh.service.DiaryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diary")
@Slf4j
@AllArgsConstructor
public class DiaryController {

    private DiaryService diaryService;



}
