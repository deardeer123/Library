package com.green.Library.web.webHome.controller;

import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebHomeController {

    @Resource(name ="webMenuService")
    WebMenuService webMenuService;


    @GetMapping("/home")
    public String goHome(Model model , BookSearchVO bookSearchVO){
        //드가기전 메뉴 정보좀 들고옴
        //제대로 들고가는지 확인
        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
        webMenuService.selectWebMenuList("member");
        System.out.println(webMenuService.selectWebMenuList("member"));
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));


        //검색 기능 필요해요



        System.out.println("홈");
        return "content/homePage/home";
    }






}
