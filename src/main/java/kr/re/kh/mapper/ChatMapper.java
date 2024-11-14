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

    void createRoom(RoomVO roomVO);
    void saveMsg(MessageVO messageVO);
    List<MessageVO> messageSearch(String roomID);
    String checkUUID(HashMap<String, Object> map);

}
