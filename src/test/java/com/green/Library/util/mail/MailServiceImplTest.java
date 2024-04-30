package com.green.Library.util.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceImplTest {
    @Autowired
    MailService mailService;

    @Test
    public void 메일발송확인(){
        try{
            String code = mailService.sendSimpleMessage("adad4852@naver.com");
            System.out.println(code);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}