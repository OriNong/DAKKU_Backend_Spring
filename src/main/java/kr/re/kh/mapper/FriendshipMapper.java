package kr.re.kh.mapper;

import kr.re.kh.model.vo.FriendshipVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FriendshipMapper {

    void saveFriendship(FriendshipVO friendShip);

    Long selectFriendshipID(Long userId, Long friendId);

    void setRequestedIds(Long fromId, Long toId);

    List<FriendshipVO> findFriendshipListByUserId(Long userId);

    // Friendship 고유 ID로 친구 관계 찾기
    Optional<FriendshipVO> findFriendshipById(Long friendshipId);

    // 친구 수락 및 거절 시 상태 변경
    void changeFriendshipStatus(FriendshipVO friendShip);

    // 친구 Accept 목록
    List<FriendshipVO> getFriendshipList(Long userId);
}
