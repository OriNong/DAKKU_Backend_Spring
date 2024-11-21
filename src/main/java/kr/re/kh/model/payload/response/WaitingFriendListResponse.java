package kr.re.kh.model.payload.response;

import kr.re.kh.model.FriendshipStatus;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class WaitingFriendListResponse {
    private Long friendshipId;
    private String friendEmail;
    private String friendName;
    private FriendshipStatus status;
    // img 필드 추후 추가
}
