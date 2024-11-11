package kr.re.kh.model.vo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RoomVO {
    private String roomId;
    private Long userID;
    private String userName;
    private Date createDate;

    @Builder
    public RoomVO(String roomId, Long userID, String userName) {
        this.roomId = roomId;
        this.userID = userID;
        this.userName = userName;
    }
}
