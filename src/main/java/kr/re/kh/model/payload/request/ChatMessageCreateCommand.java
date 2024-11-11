package kr.re.kh.model.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessageCreateCommand {
    private Long roomId;
    private String content;
    private String from;
    private Long userId;

    @Builder
    public ChatMessageCreateCommand(Long roomId, String content, String from, Long userId) {
        this.roomId = roomId;
        this.content = content;
        this.from = from;
        this.userId = userId;
    }
}
