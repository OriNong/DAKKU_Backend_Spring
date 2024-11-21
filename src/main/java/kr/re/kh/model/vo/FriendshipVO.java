package kr.re.kh.model.vo;

import kr.re.kh.model.FriendshipStatus;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendshipVO {
    private Long id;           // 일련번호
    private Long userId;       // 사용자 ID
    private Long friendId;     // 친구 ID
    private FriendshipStatus status;     // 친구 관계 상태 (Enum 대신 String)
    private boolean isFrom;    // 요청 발신 여부
    private Long requestedId;  // 요청 ID

    public void acceptFriendship() {
        status = FriendshipStatus.ACCEPT;
    }

    public void rejectFriendship() {
        status = FriendshipStatus.REJECTED;
    }
}
