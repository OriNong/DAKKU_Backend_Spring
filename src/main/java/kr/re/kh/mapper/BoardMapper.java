package kr.re.kh.mapper;

import kr.re.kh.model.vo.Board;
import kr.re.kh.model.vo.SearchHelper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    List<Board> selectBoard(SearchHelper searchHelper);

    int countBoard(SearchHelper searchHelper);

    void boardSave(Board board);

    Optional<Board> boardInfo(Long id);

    void updateBoard(Board board);

    void deleteBoard(Long id);

}
