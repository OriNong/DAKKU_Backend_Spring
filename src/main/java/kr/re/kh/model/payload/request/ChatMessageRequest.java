package kr.re.kh.model.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessageRequest {
    private Long roomId;
    private String text;
    private String from;

    @Builder
    public ChatMessageRequest(Long roomId, String text, String from) {
        this.roomId = roomId;
        this.text = text;
        this.from = from;
    }
}
