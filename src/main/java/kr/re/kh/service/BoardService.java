package kr.re.kh.service;

import kr.re.kh.model.CustomUserDetails;
import kr.re.kh.model.payload.request.BoardRequest;
import kr.re.kh.model.vo.Board;
import kr.re.kh.model.vo.SearchHelper;

import java.util.HashMap;
import java.util.List;

public interface BoardService {

    HashMap<String, Object> selectBoard(SearchHelper searchHelper);

    void saveBoard(CustomUserDetails currentUser, BoardRequest boardRequest);

    HashMap<String, Object> boardInfo(Long id);

    void deleteBoard(Long id);

}
