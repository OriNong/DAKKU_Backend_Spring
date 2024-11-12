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

    @Builder
    public ChatMessageRequest(String roomId, String text, Long userID, Long friendID) {
        this.roomId = roomId;
        this.text = text;
        this.userID = userID;
        this.friendID = friendID;
    }
}
