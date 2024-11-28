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

    @Builder
    public ChatListVO(String roomId, String userName, Long friendId, boolean me, String friendName) {
        this.roomId = roomId;
        this.userName = userName;
        this.friendId = friendId;
        this.me = me;
        this.friendName = friendName;
    }
}
