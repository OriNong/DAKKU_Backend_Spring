package kr.re.kh.model.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessageRequest {
    private String roomId;
    private String text;
    private Long userID;
    private Long friendID;
    private String userName;
    private String friendName;

    @Builder
    public ChatMessageRequest(String roomId, String text, Long userID, Long friendID, String userName, String friendName) {
        this.roomId = roomId;
        this.text = text;
        this.userID = userID;
        this.friendID = friendID;
        this.userName = userName;
        this.friendName = friendName;
    }
}
