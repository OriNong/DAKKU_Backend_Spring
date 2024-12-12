package kr.re.kh.service;

import kr.re.kh.mapper.ChatMapper;
import kr.re.kh.mapper.FriendshipMapper;
import kr.re.kh.model.CustomUserDetails;
import kr.re.kh.model.payload.request.ChatMessageCreateCommand;
import kr.re.kh.model.vo.ChatListVO;
import kr.re.kh.model.vo.MessageVO;
import kr.re.kh.model.vo.RoomVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class ChatService {
    private final ChatMapper chatMapper;
    private final FriendshipMapper friendshipMapper;

    /**
     * 유저가 채팅을 입력하면 해당 채팅을 db로 저장.
     * @param chatMessageCreateCommand
     * @return
     */
    public HashMap<String, Object> saveMsg(ChatMessageCreateCommand chatMessageCreateCommand) {
        HashMap<String, Object> result = new HashMap<>();

        // 1. 방 번호가 없으면 방을 만들고 방금 받은 메시지를  db에 넣고
        // 여부는 변수에 null로 찾는다 roomResult에 null이라면 밑에 처럼 방을 만든다.
        // 1번의 구조는 방테이블, 메시지 테이블 필요
        // 2. 생성된 방에 메시지 목록을 쿼리해서 리턴
        // 방테이블
        // roomId, 방 만든사람 id
        // 메시지 테이블
        // roomId, 메시지, 작성자, 년월일

        MessageVO messageSave = MessageVO.builder()
                .roomID(chatMessageCreateCommand.getRoomID())
                .userID(chatMessageCreateCommand.getUserId())
                .friendID(chatMessageCreateCommand.getFriendId())
                .message(chatMessageCreateCommand.getContent())
                .build();
        log.info(messageSave.toString());
        chatMapper.saveMsg(messageSave);

        result.put("uuid", chatMessageCreateCommand.getRoomID());
        result.put("userID", chatMessageCreateCommand.getUserId());
        result.put("FriendID", chatMessageCreateCommand.getFriendId());
        result.put("message", chatMessageCreateCommand.getContent());
        return result;
    }

    /**
     * 유저의 채팅방이 없으면 생성, 있으면 기존 채팅방을 불러와서 반환
     * @param currentUser
     * @param friendID
     * @return
     */
    public HashMap<String, Object> checkRoom(CustomUserDetails currentUser, Long friendID) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> requestMap = new HashMap<>();

        requestMap.put("userID", currentUser.getId());
        requestMap.put("friendID", friendID);

        String resultUUID = chatMapper.checkUUID(requestMap);
        if (StringUtils.isEmpty(resultUUID)) {
            String uuid = UUID.randomUUID().toString();
            RoomVO roomVO = RoomVO.builder()
                    .roomId(uuid)
                    .userID(currentUser.getId())
                    .friendID(friendID) // friend_id 대화하고자하는 상대방의 id를 기입
                    .build();
            chatMapper.createRoom(roomVO);
            result.put("roomId", uuid);
        } else {
            result.put("roomId", resultUUID);
            ChatMessageCreateCommand chatMessageCreateCommand = ChatMessageCreateCommand.builder()
                    .roomID(resultUUID)
                    .build();
            List<MessageVO> msgList = msgList(chatMessageCreateCommand);
            result.put("list", msgList);
        }
        result.put("userID", currentUser.getId());
        result.put("friendID", friendID);

        return result;
    }

    /**
     * 유저의 채팅방 목록 불러오기
     * @param userID
     * @return
     */
    public List<ChatListVO> userRoomCount(Long userID) {
        return chatMapper.userRoomCount(userID);
    }

    /**
     * 채팅 메세지 목록 불러오기
     * @param chatMessageCreateCommand
     * @return
     */
    public List<MessageVO> msgList(ChatMessageCreateCommand chatMessageCreateCommand) {
        return chatMapper.messageSearch(chatMessageCreateCommand.getRoomID());
    }

    public List<HashMap<String, Object>> getFriendList(Long userId) {
        List<HashMap<String, Object>> friendship = friendshipMapper.getFriendshipList(userId);

        List<HashMap<String, Object>> friendshipResult = new ArrayList<>();
        for (HashMap<String, Object> item : friendship) {
            HashMap<String, Object> temp = new HashMap<>();
            String friendName = item.get("FRIEND_NAME") == null ? null : (String) item.get("FRIEND_NAME");
            String roomId = item.get("ROOM_ID") == null ? null : (String) item.get("ROOM_ID");
            String userName = item.get("USERNAME") == null ? null : (String) item.get("USERNAME");
            BigDecimal userID = item.get("USER_ID") == null ? null : (BigDecimal) item.get("USER_ID");
            BigDecimal friendId = item.get("FRIEND_ID") == null ? null : (BigDecimal) item.get("FRIEND_ID");

            temp.put("friendName", friendName);
            temp.put("roomId", roomId);
            temp.put("friendId", friendId);
            temp.put("userName", userName);
            temp.put("userID", userID);
            friendshipResult.add(temp);
        }
        return friendshipResult;
    }

}
