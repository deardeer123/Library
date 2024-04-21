package com.green.Library.security;

import com.green.Library.web.member.service.MemberService;
import com.green.Library.web.member.vo.MemberVO;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
                                            System.out.println("@@@@@@@@@@@");
                                            HttpSession session = request.getSession();

                                            MemberVO loginInfo = memberService.login(authentication.getName());
                                            System.out.println(loginInfo);
                                            session.setAttribute("userCode", loginInfo.getUserCode());
                                            session.setAttribute("userName", loginInfo.getUserName());
                                            session.setAttribute("userId", loginInfo.getUserId());


                                            response.sendRedirect("/home");
                                        }
                                    })
                                    .failureHandler(loginFailHandler)
                                    .usernameParameter("userId")
                                    .passwordParameter("userPw");
                                    //.defaultSuccessUrl("/home");
                        }
                );
        return security.build();
    }





}
