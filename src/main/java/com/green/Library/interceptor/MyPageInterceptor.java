package com.green.Library.interceptor;

import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

//마이페이지 사이드 메뉴 던져줘야 함
public class MyPageInterceptor implements HandlerInterceptor {
    @Resource(name = "WebMenuService")
    WebMenuService webMenuService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Optional<ModelAndView> modelAndViewOptional = Optional.ofNullable(modelAndView);


    }
}
