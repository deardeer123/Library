package com.green.Library.interceptor;

import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;


public class BookAdminInterceptor implements HandlerInterceptor {
    @Resource(name = "menuService")
    LibraryMenuService libraryMenuService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //책 관리 페이지 메뉴를 던져주는 작업을 할것

        System.out.println("책관리홈페이지 인터셉터");
        //컨트롤러이 html로 보낼려는 page 정보를 pageName에 저장한다.
        Optional<ModelAndView> modelAndViewOptional = Optional.ofNullable(modelAndView);
        String pageName = modelAndViewOptional.map(s->s.getModel()).map(s->s.get("page")).map(s->s.toString()).orElse("notUseMenu");

        //메뉴를 쓰는 경우
        if(!pageName.equals("notUseMenu")){
            //pageName를 menuservice의 쿼리를 실행하는 메소드 매개변수로 넘겨줘서 헤더 메뉴의 인덱스 번호랑 사이드 번호를 가져온다.그리고 Map에 저장
            Optional<Map<String, Integer>> optionalMap =Optional.ofNullable(libraryMenuService.selectIndexNum(pageName));

            //메뉴의 인덱스, 사이드메뉴의 인덱스
            int selectedMenuIndex = optionalMap.map(s-> s.get("MENU_INDEX")).orElse(0);
            int selectedSideMenuIndex = optionalMap.map(s-> s.get("SIDE_MENU_INDEX")).orElse(0);

            //컨트롤러에서 model를 사용하여 html로 데이터를 넘겨주는거 처럼 modelAndView 써서 보내준다
            modelAndView.addObject("selectedMenuIndex", selectedMenuIndex);
            modelAndView.addObject("selectedSideMenuIndex", selectedSideMenuIndex);

            //메뉴, 상단 네비게이션 정보 던져주기
            modelAndView.addObject("menuList", libraryMenuService.selectLibraryMenuList2("library"));

        }
    }
}
