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

    private String userEmail; // user의 어떤 값을 기준으로 서로를 판별할 건지 결정 보류
    private String friendEmail;
    private FriendshipStatus status;
    private boolean isFrom;

    private Long requestUserId;

    public void acceptFriendshipRequest() {
        status = FriendshipStatus.ACCEPT;
    }

    public void setRequestUserId(Long id) {
        requestUserId = id;
    }
}
