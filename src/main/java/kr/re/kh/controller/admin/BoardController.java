package kr.re.kh.controller.admin;

import kr.re.kh.annotation.CurrentUser;
import kr.re.kh.model.CustomUserDetails;
import kr.re.kh.model.payload.request.BoardRequest;
import kr.re.kh.model.payload.response.ApiResponse;
import kr.re.kh.model.vo.SearchHelper;
import kr.re.kh.service.BoardService;
import kr.re.kh.service.FileMapService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@Slf4j
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시물 목록
     * @param request
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    public ResponseEntity<?> boardList(
        @ModelAttribute SearchHelper request
    ) {
        return ResponseEntity.ok(boardService.selectBoard(request));

    }

    /**
     * 게시물 조회
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    public ResponseEntity<?> boardView(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.boardInfo(id));
    }

    /**
     * 저장
     * @param currentUser
     * @param boardRequest
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SYSTEM')")
    public ResponseEntity<?> boardSave(@CurrentUser CustomUserDetails currentUser, @RequestBody BoardRequest boardRequest) {
        boardService.saveBoard(currentUser, boardRequest);
        return ResponseEntity.ok(new ApiResponse(true, "저장 되었습니다."));
    }

    /**
     * 삭제 (파일 포함)
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SYSTEM')")
    public ResponseEntity<?> boardDelete(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok(new ApiResponse(true, "삭제 되었습니다."));
    }

}
