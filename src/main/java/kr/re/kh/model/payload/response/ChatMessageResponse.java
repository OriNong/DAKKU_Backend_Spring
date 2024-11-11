package kr.re.kh.model.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessageResponse {

    private Long id;
    private String content;
    private String writer;

    @Builder
    public ChatMessageResponse(Long id, String content, String writer) {
        this.id = id;
        this.content = content;
        this.writer = writer;
    }


}
