package kr.re.kh.model.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipVO {
    private int memberId1;              // 사용자 1
    private int memberId2;              // 사용자 2
    private String friendshipStatus;    // 친구 상태
}
