package com.green.Library.interceptor;

import com.green.Library.web.webMenu.service.WebMenuService;
import com.green.Library.web.webMenu.vo.WebHeaderMenuVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class WebInterceptor implements HandlerInterceptor {
    @Resource(name = "webMenuService")
    WebMenuService webMenuService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //도서관 홈페이지 메뉴를 던져주는 작업을 할것

        //컨트롤러이 html로 보낼려는 page 정보를 pageName에 저장한다.
        Optional<ModelAndView> modelAndViewOptional = Optional.ofNullable(modelAndView);
        String pageName = modelAndViewOptional.map(s->s.getModel()).map(s->s.get("page")).map(s->s.toString()).orElse("notUseMenu");

        //메뉴를 쓰는 경우
        if(!pageName.equals("notUseMenu")){
            //pageName를 menuservice의 쿼리를 실행하는 메소드 매개변수로 넘겨줘서 헤더 메뉴의 인덱스 번호랑 사이드 번호를 가져온다.그리고 Map에 저장
            Optional<Map<String, Integer>> optionalMap =Optional.ofNullable(webMenuService.selectIndexNum(pageName));

            //메뉴의 인덱스, 사이드메뉴의 인덱스
            int selectedMenuIndex = optionalMap.map(s-> s.get("MENU_INDEX")).orElse(0);
            int selectedSideMenuIndex = optionalMap.map(s-> s.get("SIDE_MENU_INDEX")).orElse(0);

            System.out.println(selectedMenuIndex);
            System.out.println(selectedSideMenuIndex);

            //컨트롤러에서 model를 사용하여 html로 데이터를 넘겨주는거 처럼 modelAndView 써서 보내준다
            modelAndView.addObject("selectedMenuIndex", selectedMenuIndex);
            modelAndView.addObject("selectedSideMenuIndex", selectedSideMenuIndex);

            //메뉴, 상단 네비게이션 정보 던져주기
            modelAndView.addObject("menuList", webMenuService.selectWebMenuList("web"));
            modelAndView.addObject("memberMenuList", webMenuService.selectWebMenuList("member"));
        }
    }
}
