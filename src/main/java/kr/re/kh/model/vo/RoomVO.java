package kr.re.kh.model.vo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RoomVO {
    private Long roomId;
    private Long userID;
    private String userName;
    private Date createDate;

    @Builder
    public RoomVO(Long userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }
}
