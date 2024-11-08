package kr.re.kh.service.impl;

import kr.re.kh.exception.BadRequestException;
import kr.re.kh.mapper.BoardMapper;
import kr.re.kh.model.CustomUserDetails;
import kr.re.kh.model.payload.request.BoardRequest;
import kr.re.kh.model.payload.request.FileDeleteRequest;
import kr.re.kh.model.vo.Board;
import kr.re.kh.model.vo.FileMap;
import kr.re.kh.model.vo.SearchHelper;
import kr.re.kh.model.vo.UploadFile;
import kr.re.kh.service.BoardService;
import kr.re.kh.service.FileMapService;
import kr.re.kh.service.UploadFileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final UploadFileService uploadFileService;
    private final FileMapService fileMapService;

    /**
     * 게시물 목록 + 카운트
     * @param searchHelper
     * @return
     */
    @Override
    public HashMap<String, Object> selectBoard(SearchHelper searchHelper) {
        HashMap<String, Object> resultMap = new HashMap<>();

        float totalElements = (float) boardMapper.countBoard(searchHelper);

        resultMap.put("list", boardMapper.selectBoard(searchHelper));
        resultMap.put("totalElements", totalElements);
        resultMap.put("size", searchHelper.getSize());
        resultMap.put("totalPages", Math.ceil(totalElements / searchHelper.getSize()));
        resultMap.put("last", searchHelper.getPage() >= searchHelper.getSize());
        return resultMap;
    }

    /**
     * 게시물 저장, 수정
     * @param currentUser
     * @param boardRequest
     */
    @Override
    public void saveBoard(CustomUserDetails currentUser, BoardRequest boardRequest) {
        log.info("saveBoard");
        log.info(boardRequest.toString());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date startDate = null;
        try {
            startDate = formatter.parse(boardRequest.getStartDate());
        } catch (ParseException e) {
            throw new BadRequestException("날짜 형식이 잘못되었습니다.");
        }
        Date endDate = null;
        try {
            endDate = formatter.parse(boardRequest.getEndDate());
        } catch (ParseException e) {
            throw new BadRequestException("날짜 형식이 잘못되었습니다.");
        }
        if (boardRequest.getId() == 0) {
            // 저장
            Board board = Board.builder()
                    .code(boardRequest.getCode())
                    .title(boardRequest.getTitle())
                    .content(boardRequest.getContent())
                    .startDate(startDate)
                    .endDate(endDate)
                    .regId(currentUser.getUsername())
                    .build();

            boardMapper.boardSave(board);

            Long[] fileId = boardRequest.getFiles();

            for (Long aLong : fileId) {
                FileMap fileMap = FileMap.builder()
                        .boardId(board.getId())
                        .fileId(aLong)
                        .fileTarget(boardRequest.getFileTarget())
                        .build();
                fileMapService.insertFileMap(fileMap);
            }
        } else {
            // 수정
            Board board = Board.builder()
                    .id(boardRequest.getId())
                    .code(boardRequest.getCode())
                    .title(boardRequest.getTitle())
                    .content(boardRequest.getContent())
                    .startDate(startDate)
                    .endDate(endDate)
                    .modId(currentUser.getUsername())
                    .build();

            boardMapper.updateBoard(board);

            Long[] fileId = boardRequest.getFiles();

            log.info(String.valueOf(fileId.length));

            for (Long aLong : fileId) {
                FileMap fileMap = FileMap.builder()
                        .boardId(board.getId())
                        .fileId(aLong)
                        .fileTarget(boardRequest.getFileTarget())
                        .build();
                // 파일을 체크해서 row가 존재하면 페스, 존재하지 않으면 insert
                Boolean checkFileMap = fileMapService.checkFileMap(fileMap);
                if (!checkFileMap) {
                    fileMapService.insertFileMap(fileMap);
                }
            }
        }

    }

    /**
     * 게시물 정보 + 파일목록
     * @param id
     * @return
     */
    @Override
    public HashMap<String, Object> boardInfo(Long id) {
        HashMap<String, Object> resultMap = new HashMap<>();
        Optional<Board> result = boardMapper.boardInfo(id);
        List<UploadFile> fileList;
        if (result.isPresent()) {
            fileList = uploadFileService.selectFileByBoardId(id);
        } else {
            throw new BadRequestException("해당 게시물을 찾을 수 없습니다.");
        }
        resultMap.put("info", result.get());
        resultMap.put("uploadFiles", fileList);

        return resultMap;
    }

    /**
     * 게시물 + 파일 삭제
     * @param id
     */
    @Override
    public void deleteBoard(Long id) {
        Optional<Board> info = boardMapper.boardInfo(id);
        List<UploadFile> fileList = uploadFileService.selectFileByBoardId(id);
        if (info.isPresent()) {
            log.info("삭제한다 + " + fileList.size());
            boardMapper.deleteBoard(id);
            for(UploadFile item : fileList) {
                FileDeleteRequest fileDeleteRequest = new FileDeleteRequest();
                fileDeleteRequest.setId(item.getId());
                fileDeleteRequest.setFileTarget(item.getFileTarget());
                uploadFileService.deleteAsResource(fileDeleteRequest);
            }
        } else {
            throw new BadRequestException("해당 게시물을 찾을 수 없습니다.");
        }
    }

}
