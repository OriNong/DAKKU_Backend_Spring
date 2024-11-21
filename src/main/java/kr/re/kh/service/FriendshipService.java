package kr.re.kh.service;

import kr.re.kh.exception.BadRequestException;
import kr.re.kh.mapper.FriendshipMapper;
import kr.re.kh.model.FriendshipStatus;
import kr.re.kh.model.User;
import kr.re.kh.model.vo.FriendshipVO;
import kr.re.kh.model.payload.response.WaitingFriendListResponse;
import kr.re.kh.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipMapper friendshipMapper;

    /**
     * 친구 요청 전송
     * @param requesterId
     * @param receiverId
     * @return
     */
    public boolean createFriendship(Long requesterId, Long receiverId) {

        if (requesterId.equals(receiverId)) throw new BadRequestException("자기 자신에게 친구 요청을 보낼 수 없습니다.");

        // 친구 요청을 보낸 사용자
        Optional<User> fromUser = userRepository.findById(requesterId);
        // 친구 요청을 받은 사용자
        Optional<User> toUser = userRepository.findById(receiverId);

        if (fromUser.isPresent() && toUser.isPresent()) {
            log.info("***" + fromUser.get().getId().toString() + "***");
            // 요청자의 친구 관계 생성 및 저장
            FriendshipVO friendshipFrom = FriendshipVO.builder()
                    .userId(fromUser.get().getId())
                    .friendId(toUser.get().getId())
                    .status(FriendshipStatus.WAITING)
                    .isFrom(true)
                    .build();
            log.info("============================================================");
            log.info("요청자의 친구 관계 생성 및 저장");
            log.info("============================================================");
            friendshipMapper.saveFriendship(friendshipFrom);

            // 수신자의 친구 관계 생성 및 저장
            log.info("***" + toUser.get().getId().toString() + "***");
            FriendshipVO friendshipTo = FriendshipVO.builder()
                    .userId(toUser.get().getId())
                    .friendId(fromUser.get().getId())
                    .status(FriendshipStatus.WAITING)
                    .isFrom(false)
                    .build();
            log.info("============================================================");
            log.info("수신자의 친구 관계 생성 및 저장");
            log.info("============================================================");
            friendshipMapper.saveFriendship(friendshipTo); // -> 여기선 자동으로 requested_id 설정됌

            // 위에서 각각 생성된 친구 관계 고유 id로 양방향 REQUESTED_ID 업데이트
            Long friendshipFromId = friendshipMapper.selectFriendshipID(fromUser.get().getId(), toUser.get().getId());
            Long friendshipToId = friendshipMapper.selectFriendshipID(toUser.get().getId(), fromUser.get().getId());
            log.info("============================================================");
            log.info("위에서 각각 생성된 친구 관계 고유 id로 양방향 REQUESTED_ID 업데이트");
            log.info("보낸 친구 요청 id : " + friendshipFromId);
            log.info("받은 친구 요청 id : " + friendshipToId);
            log.info("============================================================");
            friendshipMapper.setRequestedIds(friendshipFromId, friendshipToId);

            return true;
        } else {
            throw new BadRequestException("유저 정보 조회 오류");
        }
    }

    /**
     * 받은 친구 요청 중 수락되지 않은 요청을 조회
     * @param receivedId : Controller에서 받은 현재 로그인 userId
     * @return : 조회된 결과를 담은 객체 리스트
     */
    @Transactional
    public ResponseEntity<?> getWaitingFriendList(Long receivedId) {
        // 현재 로그인 한 유저의 정보를 가져온다.
        //User loginUser = userRepository.findById(receivedId).orElseThrow(() -> new BadRequestException("회원 조회 실패"));
        Optional<User> loginUser = userRepository.findById(receivedId);
        if (loginUser.isPresent()) {
            List<FriendshipVO> friendShipList = friendshipMapper.findFriendshipListByUserId(loginUser.get().getId());
            if (!friendShipList.isEmpty()) {
                // 조회된 결과를 담을 VO 리스트
                List<WaitingFriendListResponse> waitingFriendListResponse = new ArrayList<>();
                for (FriendshipVO fs : friendShipList) {
                    // 자신이 보낸 요청이 아니면서 수락 대기 중인 요청만 조회
                    if (!fs.isFrom() && fs.getStatus() == FriendshipStatus.WAITING) {
                        Optional<User> friend = userRepository.findById(fs.getFriendId());
                        if (friend.isPresent()) {
                            WaitingFriendListResponse wf = WaitingFriendListResponse.builder()
                                    .friendshipId(fs.getId())
                                    .friendEmail(friend.get().getEmail())
                                    .friendName(friend.get().getName())
                                    .status(fs.getStatus())
                                    .build();
                            waitingFriendListResponse.add(wf);
                        }
                    }
                }
                return new ResponseEntity<>(waitingFriendListResponse, HttpStatus.OK);
            }else throw new BadRequestException("수락 대기 중인 친구 요청이 없습니다");
        } else throw new BadRequestException("회원 조회 실패");
    }

    /**
     * 친구 요청 수락
     * @param receivedFriendshipId : 받은 친구 요청의 고유 id
     * @return :
     */
    public ResponseEntity<?> acceptFriendshipRequest(Long receivedFriendshipId) {
        // 받은 친구 요청
        Optional<FriendshipVO> receivedFriendship = friendshipMapper.findFriendshipById(receivedFriendshipId);
        if (receivedFriendship.isPresent()) {
            // 보낸 친구 요청
            Optional<FriendshipVO> requestedFriendship = friendshipMapper.findFriendshipById(receivedFriendship.get().getRequestedId());
            if (requestedFriendship.isPresent()) {
                // 받은 친구 요청 상태를 WAITING -> ACCEPT 변경
                receivedFriendship.get().acceptFriendship();
                // DB 업데이트
                friendshipMapper.changeFriendshipStatus(receivedFriendship.get());
                // 보낸 친구 요청 상태를 WAITING -> ACCEPT 변경
                requestedFriendship.get().acceptFriendship();
                // DB 업데이트
                friendshipMapper.changeFriendshipStatus(requestedFriendship.get());
            } else {
                throw new BadRequestException("상대방이 보낸 친구 요청이 존재하지 않습니다");
            }
        } else {
            throw new BadRequestException("수신된 친구 요청이 존재하지 않습니다");
        }
        return new ResponseEntity<>("친구 수락 완료", HttpStatus.OK);
    }

    public ResponseEntity<?> rejectFriendshipRequest(Long receivedFriendshipId) {
        // 받은 친구 요청
        Optional<FriendshipVO> receivedFriendship = friendshipMapper.findFriendshipById(receivedFriendshipId);
        if (receivedFriendship.isPresent()) {
            // 보낸 친구 요청
            Optional<FriendshipVO> requestedFriendship = friendshipMapper.findFriendshipById(receivedFriendship.get().getRequestedId());
            if (requestedFriendship.isPresent()) {
                // 받은 친구 요청 상태를 WAITING -> REJECTED 변경
                receivedFriendship.get().rejectFriendship();
                // DB 업데이트
                friendshipMapper.changeFriendshipStatus(receivedFriendship.get());
                // 보낸 친구 요청 상태를 WAITING -> REJECTED 변경
                requestedFriendship.get().rejectFriendship();
                // DB 업데이트
                friendshipMapper.changeFriendshipStatus(requestedFriendship.get());
            } else {
                throw new BadRequestException("상대방이 보낸 친구 요청이 존재하지 않습니다");
            }
        } else {
            throw new BadRequestException("수신된 친구 요청이 존재하지 않습니다");
        }
        return new ResponseEntity<>("친구 요청이 거절되었습니다.", HttpStatus.OK);
    }
}
