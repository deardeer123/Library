package com.green.Library.util.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Configuration
public class MailConfig {

    //참고한 사이트https://velog.io/@dm911/Spring-Boot-SMTP-%EC%84%9C%EB%B2%84%EB%A5%BC-%ED%86%B5%ED%95%9C-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%9D%B8%EC%A6%9D%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84
    @Bean
    public JavaMailSender NaverMailService(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.naver.com"); //smtp 서버명
        javaMailSender.setUsername("rivenop");//네이버아이디 입력
        javaMailSender.setPassword("QEGWGJC8EGRE"); //네이버비빌번호 애플리케이션 비밀번호

        javaMailSender.setPort(465);//smtp 포트
        javaMailSender.setJavaMailProperties(getMailProperties()); // 메일 인증서버 가져오기
        return javaMailSender;

    }
    // 메일 인증서버 정보 가져오기
    private Properties getMailProperties(){
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp"); // 프로토콜 설정
        properties.setProperty("mail.smtp.auth", "true"); // smtp 인증
        properties.setProperty("mail.smtp.starttls.enable", "true"); // smtp strattles 사용
        properties.setProperty("mail.debug", "true"); // 디버그 사용
        properties.setProperty("mail.smtp.ssl.trust", "smtp.naver.com"); // ssl 인증 서버 (smtp 서버명)
        properties.setProperty("mail.smtp.ssl.enable", "true"); // ssl 사용

        return properties;
    }

}
