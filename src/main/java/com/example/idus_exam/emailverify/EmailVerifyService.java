package com.example.idus_exam.emailverify;


import com.example.idus_exam.emailverify.model.EmailVerify;
import com.example.idus_exam.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final JavaMailSender mailSender;
    private final EmailVerifyRepository emailVerifyRepository;

    public void sendEmail(String uuid, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("환영합니다!");
        message.setText(
                "http://localhost:8080/user/verify?uuid=" + uuid
        );

        mailSender.send(message);
    }
    public void signup(Long idx, String email) {
        String uuid = UUID.randomUUID().toString();

        User user = User.builder()
                .idx(idx)
                .build();

        // 이메일 인증 정보 저장
        emailVerifyRepository.save(EmailVerify.builder()
                .user(user)
                .uuid(uuid)
                .build());

        // 이메일 전송
        sendEmail(uuid, email);
    }

    public User verify(String uuid) {
        EmailVerify emailVerify = emailVerifyRepository.findByUuid(uuid).orElseThrow();

        return emailVerify.getUser();
    }
}
