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
        //메뉴 정보 보내기
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //로그인 네비게이션 정보.
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));



        //검색 기능 필요해요



        System.out.println("홈");
        return "content/homePage/home";
    }






}
