package kr.re.kh.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender javaMailSender;

    /**
     * 인증번호 생성, 메일 전송 서비스.
     * @param mail
     * @return
     * @throws MessagingException
     */
    public int sendVerificationCode(String mail) throws MessagingException {
        // 랜덤 코드 생성.
        int verificationCode = new Random().nextInt(900000) + 100000;

        // 이메일 메시지 생성
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // 메일 설정.
        helper.setTo(mail); // 메일을 보낼사람.
        helper.setSubject("Your Verification Code"); // 제목
        helper.setText("Your Verification Code is : " + verificationCode); // 메일 내용

        // 이메일 전송
        javaMailSender.send(message);

        return verificationCode;
    }
}
