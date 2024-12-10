package kr.re.kh.mapper;

import kr.re.kh.model.vo.ChatListVO;
import kr.re.kh.model.vo.MessageVO;
import kr.re.kh.model.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ChatMapper {

    void createRoom(RoomVO roomVO);
    void saveMsg(MessageVO messageVO);
    void createAlarmCount(HashMap<String, Object> roomId);
    List<MessageVO> messageSearch(String roomID);
    String checkUUID(HashMap<String, Object> map);
    List<ChatListVO> userRoomCount(Long userID);

}
