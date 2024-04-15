package com.green.Library.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //책 관리 페이지 인터셉터
        registry.addInterceptor(getWebInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/**Fetch")
                .excludePathPatterns("/bookAdmin/**");

        //도서관 홈페이지 입터셉터
        registry.addInterceptor(getBookAdminInterceptor())
                   .addPathPatterns("/bookAdmin/**")
                    .excludePathPatterns("/bookAdmin/**Fetch");

//                .addPathPatterns("/bookAdmin/workingBook");
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/**Fetch")
                .excludePathPatterns("/bookAdmin/**");

    }

    @Bean
    BookAdminInterceptor getBookAdminInterceptor(){
        return new BookAdminInterceptor();
    }

    @Bean
    WebInterceptor getWebInterceptor(){
        return new WebInterceptor();
    }

    @Bean
    LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }

}
