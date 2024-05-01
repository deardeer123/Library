package com.green.Library.util.mail;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimpleMailServiceImplTest {

    @Autowired
    SimpleMailService simpleMailService;

    @Test
    public void 이메일전송_테스트() throws MessagingException, UnsupportedEncodingException {
        String email = "adad4852@naver.com";
        String text = "메일전송 입니다.";
        String subject = "메일 제목";

        simpleMailService.SimpleMailSend(email,text,subject);
    }
}