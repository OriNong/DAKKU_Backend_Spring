package kr.re.kh.model.vo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Setter
@Slf4j
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {

    private Long userId;
    private String email;
    private boolean isEmailVerified;
    private String username;
    private String password;
    private String name;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
