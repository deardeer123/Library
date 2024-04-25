package com.green.Library.security;

import com.green.Library.web.member.service.MemberService;
import com.green.Library.web.member.vo.MemberVO;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cgonfig.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Resource(name = "memberService")
    private MemberService memberService;

    //로그인 실패시 어떤 클래스를 실행 시킬 수 있게 LoginFailHandler 스프링빈을 주입 객체를 주입?
    LoginFailHandler loginFailHandler;

    @Autowired
    public SecurityConfig(LoginFailHandler loginFailHandler){
        this.loginFailHandler = loginFailHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{


        security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        c -> {
                            c.anyRequest().permitAll();
                        }
                )
                .formLogin(
                        formLogin -> {
                            formLogin.loginPage("/login")
                                    .loginProcessingUrl("/loginForm")
                                    .successHandler(new AuthenticationSuccessHandler() {
                                        @Override
                                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                            HttpSession session = request.getSession();

                                            MemberVO loginInfo = memberService.login(authentication.getName());
                                            System.out.println(loginInfo);
                                            session.setAttribute("userCode", loginInfo.getUserCode());
                                            session.setAttribute("userName", loginInfo.getUserName());
                                            session.setAttribute("userId", loginInfo.getUserId());



                                            response.sendRedirect("/home");
                                        }
                                    })
                                    .failureHandler(loginFailHandler) //로그인 실패 시 실행시킬 클래스의 객체?
                                    .usernameParameter("userId")
                                    .passwordParameter("userPw");
                                    //.defaultSuccessUrl("/home");
                        }
                );
        return security.build();
    }

    //정적인 자원은 security가 인증 및 인가 관리에서 제외하도록 설정
    //정적인 파일 : .js, .css , 이미지
    //resources 폴더 밑에 있는 모든 파일들
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web->web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/upload/**"),
                new AntPathRequestMatcher("/css/**"),
                new AntPathRequestMatcher("/js/**"),
                new AntPathRequestMatcher("/favicon.ico"),
                new AntPathRequestMatcher("/datepicker/**"),
                new AntPathRequestMatcher("/ckeditor5/**")
        );
    }




}
