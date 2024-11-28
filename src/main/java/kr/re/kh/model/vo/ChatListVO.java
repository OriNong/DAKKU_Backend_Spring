package kr.re.kh.model.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatListVO {
    private String roomId;
    private String userName;
    private Long friendId;
    private String friendName;
    private boolean me;
}
