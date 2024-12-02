package kr.re.kh.model.vo;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessageVO {
    @JsonIgnore
    private String roomID;
    @JsonIgnore
    private Long userID;
    @JsonIgnore
    private Long friendID;
    private String message;

    @JsonIgnore
    private LocalDateTime inputDate;

    @Builder
    public MessageVO(String roomID, Long userID, Long friendID, String message, LocalDateTime inputDate) {
        this.roomID = roomID;
        this.userID = userID;
        this.friendID = friendID;
        this.message = message;
        this.inputDate = inputDate;
    }
}
