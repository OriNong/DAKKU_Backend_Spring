package kr.re.kh.model.vo;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatListVO {
    private String roomId;
    private String userName;
    private Long userID;
    private Long friendId;
    private String friendName;
    private String lastMessage;
    private LocalDate createDate;
    private boolean me;
}
