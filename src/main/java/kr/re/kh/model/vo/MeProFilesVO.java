package kr.re.kh.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MeProFilesVO {
    private String userName;
    private String proFileImg;
    private String email;
    private String name;
    private List<FriendshipVO> friendshipList;
}
