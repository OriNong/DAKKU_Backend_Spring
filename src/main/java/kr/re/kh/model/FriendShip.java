package kr.re.kh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "FRIEND_SHIP")
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

    @Column(name = "FRIEND_ID")
    private Long friendId; // 요청이 오거나 내가 요청을 전송한 상대

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private FriendshipStatus status;

    @Column(name = "IS_FROM")
    private boolean isFrom;

    @Column(name = "REQUESTED_ID")
    private Long requestedId; // 상대 요청 id

    public void acceptFriendshipRequest() {
        status = FriendshipStatus.ACCEPT;
    }

    public void setRequestedId(Long id) {
        requestedId = id;
    }
}
