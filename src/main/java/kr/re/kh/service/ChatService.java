package kr.re.kh.service;

import kr.re.kh.mapper.ChatMapper;
import kr.re.kh.model.payload.request.ChatMessageCreateCommand;
import kr.re.kh.model.payload.request.ChatMessageRequest;
import kr.re.kh.model.payload.response.ChatMessageResponse;
import kr.re.kh.model.vo.MessageVO;
import kr.re.kh.model.vo.RoomVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ChatService {

    @Autowired
    private ChatMapper chatMapper;

    public HashMap<String, Object> chat(ChatMessageCreateCommand chatMessageCreateCommand) {
        HashMap<String, Object> result = new HashMap<>();

        // 랜덤으로 uuid를 생성해서 방 코드를 정해준다.
        String uuid = UUID.randomUUID().toString();

        RoomVO Search = RoomVO.builder()
                .friendID(chatMessageCreateCommand.getFriendId())
                .userID(chatMessageCreateCommand.getUserId())
                .build();
        Optional<String> roomResult = chatMapper.roomSearch(Search);

        // 1. 방 번호가 없으면 방을 만들고 방금 받은 메시지를  db에 넣고
        // 여부는 변수에 null로 찾는다 roomResult에 null이라면 밑에 처럼 방을 만든다.
        if (roomResult.isEmpty()) {
            RoomVO roomVO = RoomVO.builder()
                    .roomId(uuid)
                    .userID(chatMessageCreateCommand.getUserId())
                    .friendID(chatMessageCreateCommand.getFriendId()) // friend_id 대화하고자하는 상대방의 id를 기입
                    .build();
            chatMapper.createRoom(roomVO);
            result.put("uuid", uuid);
        } else {
            result.put("uuid", roomResult.get().toString());
        }
        // 1번의 구조는 방테이블, 메시지 테이블 필요
        // 2. 생성된 방에 메시지 목록을 쿼리해서 리턴
        List<MessageVO> messageList = chatMapper.messageSearch(result.get("uuid").toString());

        MessageVO messageSave = MessageVO.builder()
                .roomID(result.get("uuid").toString())
                .userID(chatMessageCreateCommand.getUserId())
                .friendID(chatMessageCreateCommand.getFriendId())
                .message(chatMessageCreateCommand.getContent())
                .build();
        chatMapper.saveMsg(messageSave);
        // 방테이블
        // roomId, 방 만든사람 id
        // 메시지 테이블
        // roomId, 메시지, 작성자, 년월일

        result.put("list", messageList);
        return result;
    }

    public HashMap<String, Object> checkRoom(Long myId, Long friendID) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> requestMap = new HashMap<>();

        requestMap.put("userID", myId);
        requestMap.put("friendID", friendID);
        String resultUUID = chatMapper.checkUUID(requestMap);
        log.info(resultUUID);

        return result;
    }
}
