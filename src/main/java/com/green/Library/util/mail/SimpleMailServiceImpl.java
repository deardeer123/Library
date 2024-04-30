package com.green.Library.util.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class SimpleMailServiceImpl implements SimpleMailService{
    private JavaMailSender emailSender;
    @Autowired
    public SimpleMailServiceImpl(JavaMailSender javaMailSender){
        this.emailSender = javaMailSender;
    }

    //메일보내기
    @Override
    public void SimpleMailSend(String email, String text , String subject) throws MessagingException, UnsupportedEncodingException {
        String to = email; //받는 사람 이메일
        MimeMessage message = emailSender.createMimeMessage(); //메세지 객체 생성
        message.addRecipients(MimeMessage.RecipientType.TO, to); //받는 사람 설정
        message.setSubject(subject); // 메일제목
        String mag = text; // 메일 내용
        message.setText(mag, "utf-8"); //내용 설정
        //보내는 이메일, 보내는사람 설정
        message.setFrom(new InternetAddress("rivenop@naver.com", "도서관 관리자"));

        //예외처리
        try{
            emailSender.send(message); // 메일 보내기
        } catch(Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}
