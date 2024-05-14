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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

//    암호화에 사용하는 객체 생성
    @Bean
    public BCryptPasswordEncoder getEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{


        security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        c -> {
                            //관리자 인 경우메만 도서관리페이지로 이동.
                            c.requestMatchers(
                                    new AntPathRequestMatcher("/bookAdmin/**")
                            ).hasRole("Y")
                            .anyRequest().permitAll();
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
                )
                .logout(
                        logOut ->{
                            //해당 url 요청이 들어오면 시큐리티가 로그아웃 진행
                            logOut.logoutUrl("/logout")
                                    //로그아웃 성공 시 이동할 url
                                    .logoutSuccessUrl("/login")
                                    //로그아웃 성공 시 세션 데이터 삭제
                                    .invalidateHttpSession(true);
                        }
                ).exceptionHandling(
                        ex->{ex.accessDeniedPage("/deny");
                        }
                )
        ;
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
