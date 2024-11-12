package kr.re.kh.model.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessageCreateCommand {
    private String content;
    private Long userId;
    private Long friendId;

    @Builder
    public ChatMessageCreateCommand(String content, Long userId, Long friendId) {
        this.content = content;
        this.userId = userId;
        this.friendId = friendId;
    }
}
