package com.green.Library.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

//스프링 빈 아무튼 객체 생성을 위한 어노테이션
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
//    로그인 실패시 실행시킬 클래스 파일 AuthenticationFailureHandler 인터페이스를 구현해줘야함

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //로그인 실패 시 자동으로 호출 되는 메소드
        //만약 아이디 혹은 비밀번호를 틀린 경우에는 로그인 화면에 시뻘건 글씨로 표시해줄 예정

        //에러 메세지 문자열
        String errMsg ="";

        //인증과 관련된 예외처리
        //참고한 사이트 https://theheydaze.tistory.com/307
        // https://otrodevym.tistory.com/entry/Spring-Security-%EC%A0%95%EB%A6%AC-4-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%8B%A4%ED%8C%A8-%ED%9B%84-%EC%B2%98%EB%A6%AC

        //만약에 ~ AuthenticationException 발생한 오류가 BadCredentialsException 혹은 InternalAuthenticationServiceException 인경우
        //instanceof -> 객체 타입을 확인하는데 사용 형변환이 가능 한경우 True 가 나옴
        // BadCredentialsException -> 아이디 혹은 비밀번호 불일치
        // InternalAuthenticationServiceException -> 시스템 문제로 내부 인증 관련 처리 요청을 할 수 없는 경우
        // UsernameNotFoundException  계정이 없는 경우

        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            errMsg="아이디 또는 비밀번호가 틀렸어요";
        }else if (exception instanceof UsernameNotFoundException){
            errMsg ="존재하지 않는 사용자 ID 이예요";
        }else{
            errMsg = "알 수 없는 이유로 로그인 실패했습니다. 관리자에게 문의하세요";
        }

        //인코딩 변환
        errMsg = URLEncoder.encode(errMsg, "UTF-8");

        //이동할 페이지를 설정
        response.sendRedirect("/login?errMsg="+errMsg);
    }



}
