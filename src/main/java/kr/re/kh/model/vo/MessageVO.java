package kr.re.kh.model.vo;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

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

    @Builder
    public MessageVO(String roomID, Long userID, Long friendID, String message) {
        this.roomID = roomID;
        this.userID = userID;
        this.friendID = friendID;
        this.message = message;
    }
}
