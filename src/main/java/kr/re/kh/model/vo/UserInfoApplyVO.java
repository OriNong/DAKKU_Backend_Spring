package kr.re.kh.model.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserInfoApplyVO {
    private String name;
    private String password;
    private String passwordChk;
    private Long userId;

    @Builder
    public UserInfoApplyVO(String name, String password, String passwordChk, Long userId) {
        this.name = name;
        this.password = password;
        this.passwordChk = passwordChk;
        this.userId = userId;
    }
}
