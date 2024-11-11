package kr.re.kh.service;

import kr.re.kh.mapper.ChatMapper;
import kr.re.kh.model.payload.request.ChatMessageCreateCommand;
import kr.re.kh.model.payload.request.ChatMessageRequest;
import kr.re.kh.model.payload.response.ChatMessageResponse;
import kr.re.kh.model.vo.RoomVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class ChatService {

    @Autowired
    private ChatMapper chatMapper;

    public List<ChatMessageResponse> chat(ChatMessageCreateCommand chatMessageCreateCommand) {
        List<ChatMessageResponse> list = new ArrayList<>();

        // 랜덤 숫자를 생성해서 roomId를 정한다.
        String uuid = UUID.randomUUID().toString();

        // 1. 방 번호가 없으면 방을 만들고 방금 받은 메시지를  db에 넣고
        String roomResult = chatMapper.roomSearch(chatMessageCreateCommand.getUserId());

        if (roomResult == null) {
            RoomVO roomVO = RoomVO.builder()
                    .roomId(uuid)
                    .userID(chatMessageCreateCommand.getUserId())
                    .userName(chatMessageCreateCommand.getFrom())
                    .build();
            chatMapper.createRoom(roomVO);
        }
        // 1번의 구조는 방테이블, 메시지 테이블 필요
        // 2. 생성된 방에 메시지 목록을 쿼리해서 리턴
        // 방테이블
        // roomId, 방 만든사람 id
        // 메시지 테이블
        // roomId, 메시지, 작성자, 년월일


        return list;
    }

}
