package kr.re.kh.mapper;

import kr.re.kh.model.vo.MessageVO;
import kr.re.kh.model.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Mapper
public interface ChatMapper {

    Optional<String> roomSearch(RoomVO roomVO);
    void createRoom(RoomVO roomVO);

    List<MessageVO> messageSearch(String roomID);
    void saveMsg(MessageVO messageVO);

    String checkUUID(HashMap<String, Object> map);

}
