package kr.re.kh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private Long friendId; // 요청이 오거나 내가 요청을 전송한 상대
    private FriendshipStatus status;
    private boolean isFrom;

    private Long requestedId; // 상대 요청 id

    public void acceptFriendshipRequest() {
        status = FriendshipStatus.ACCEPT;
    }

    public void setRequestedId(Long id) {
        requestedId = id;
    }
}
