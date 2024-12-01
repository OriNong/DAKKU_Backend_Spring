package kr.re.kh.model.vo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatListVO {
    private String roomId;
    private String userName;
    private Long friendId;
    private String friendName;
    private Date createDate;
    private boolean me;
}
