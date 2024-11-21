package kr.re.kh.model.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RoomVO {
    private String roomId;
    private Long userID;
    private Long friendID;

    @Builder
    public RoomVO(String roomId, Long userID, Long friendID) {
        this.roomId = roomId;
        this.userID = userID;
        this.friendID = friendID;
    }
}
