package kr.re.kh.controller.auth;

import kr.re.kh.model.payload.response.ApiResponse;
import kr.re.kh.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    private final Map<String, Integer> verificationCodes = new ConcurrentHashMap<>();
    private final Map<String, Boolean> emailVerificationStatus = new HashMap<>();

    // 이메일 인증번호 전송.
    // RequestBody로 이메일을 전송후 서버에서 인증번호 메일을 발송한다.
    @PostMapping("/mailSend")
    public ResponseEntity<?> sendVerificationCode(@RequestBody HashMap<String, Object> mail) {
        // RequestBody로 받은 메일을 변수로 넣는다.
        String mailStr = mail.get("mail").toString();
        log.info(mailStr); // body로 받은 메일을 로그로 출력.

        try {
            int code = mailService.sendVerificationCode(mailStr); // 메일을 파라미터로 전송후 보낸 인증번호를 리턴 받는다. 그후 code로 변수 저장
            verificationCodes.put(mailStr, code); // 받은 인증번호를 위에 선언된 Map 변수로 보관후 추후 '인증번호 검증'에서 사용된다.
            return ResponseEntity.ok(new ApiResponse(true, "이메일 전송완료!")); // 정상적으로 전송되면 true를 보낸후 전송완료 메시지를 보낸다.
        } catch (MessagingException e) {
            return ResponseEntity.ok(new ApiResponse(false, "이메일을 전송하지 못했습니다.")); // 이메일을 전송하지 못하면 전송실패를 띄운다.
        }
    }

    // 인증번호 검증
    // RequestBody로 받은 mailInfo를 사용해서 인증을 거친다.
    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCode(@RequestBody HashMap<String, Object> mailInfo) {
        String mail = (String) mailInfo.get("mail"); // body로 받은 메일을 String mail에 저장.
        Integer code = Integer.parseInt((String) mailInfo.get("code")); // body로 받은 인증번호를 Integer code로 보관.

        Integer storedCode = verificationCodes.get(mail); // 발송한 인증번호를 가져와서 Integer storedCode 변수에 보관.
        if (storedCode != null && storedCode.equals(code)) { // 해당 인증번호가 비어있지 않은지 storedCode와 code가 동일한지 확인.
            verificationCodes.remove(mail); // 이메일이 정상적으로 확인이 되었다면 기존에 발송한 인증번호는 삭제.
            // 인증이 성공하면 이메일 인증 상태를 true로 업데이트
            emailVerificationStatus.put(mail, true); // 인증 상태 저장 (예: emailVerificationStatus Map)

            return ResponseEntity.ok(new ApiResponse(true, "이메일 인증 완료!")); // 그후 true를 리턴.
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "인증번호가 맞지 않습니다.")); // 인증번호가 맞지 않으면 false를 리턴.
        }
    }

}
