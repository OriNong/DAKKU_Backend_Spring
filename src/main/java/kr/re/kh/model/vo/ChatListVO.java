package kr.re.kh.model.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatListVO {
    private String roomId;
    private String userName;
    private String friendName;

    @Builder
    public ChatListVO(String roomId, String userName, String friendName) {
        this.roomId = roomId;
        this.userName = userName;
        this.friendName = friendName;
    }
}
