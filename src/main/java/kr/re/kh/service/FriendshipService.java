package kr.re.kh.service;

import kr.re.kh.exception.BadRequestException;
import kr.re.kh.model.FriendShip;
import kr.re.kh.model.FriendshipStatus;
import kr.re.kh.model.User;
import kr.re.kh.repository.FriendshipRepository;
import kr.re.kh.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public boolean createFriendship(Long requesterId, Long receiverId) {

        // 친구 요청을 보낸 사용자
        Optional<User> optionalFromUser = userRepository.findById(requesterId);
        // 친구 요청을 받은 사용자
        Optional<User> optionalToUser = userRepository.findById(receiverId);

        if (optionalFromUser.isPresent() && optionalToUser.isPresent()) {

            FriendShip friendshipFrom = FriendShip.builder()
                    .user(optionalFromUser.get())
                    .friendId(receiverId)
                    .status(FriendshipStatus.WAITING)
                    .isFrom(true)
                    .build();

            FriendShip friendshipTo = FriendShip.builder()
                    .user(optionalToUser.get())
                    .friendId(requesterId)
                    .status(FriendshipStatus.WAITING)
                    .isFrom(false)
                    .build();

            // 각각의 유저 리스트에 친구 요청 정보 저장
//            optionalFromUser.get().getFriendShipList().add(friendshipTo);
//            optionalToUser.get().getFriendShipList().add(friendshipFrom);

            friendshipRepository.save(friendshipTo);
            friendshipRepository.save(friendshipFrom);

            // 매칭되는 friendship 고유 아이디를 서로 저장
            friendshipTo.setRequestedId(friendshipFrom.getId());
            friendshipFrom.setRequestedId(friendshipTo.getId());

            return true;
        } else {
            throw new BadRequestException("유저 정보 조회 오류");
        }
    }
}
