package kr.re.kh.mapper;

import kr.re.kh.model.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {

    String roomSearch(Long userID);
    void createRoom(RoomVO roomVO);

}
