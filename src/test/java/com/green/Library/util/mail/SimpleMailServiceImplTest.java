package com.green.Library.util.mail;

import com.green.Library.web.member.service.MemberServiceImpl;
import jakarta.annotation.Resource;
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


    @Resource(name = "memberService")
    MemberServiceImpl memberService;


    @Test
    public void mail_email() throws MessagingException, UnsupportedEncodingException {

        String email = "adad4852@naver.com";
        String text = "hubuypoikjojhuyuhy";
        String subject = "메일 제jiojiojuo목";

        simpleMailService.SimpleMailSend(email,text,subject);
    }
    @Test
    public void tel_email() throws MessagingException, UnsupportedEncodingException {
        String pw = memberService.createRandomPw();
        String email = "adad4852@naver.com";
        String text = "임시 비밀번호 : "+ pw;
        String subject = "메일 제jiojiojuo목";

        simpleMailService.SimpleMailSend(email,text,subject);
    }
}