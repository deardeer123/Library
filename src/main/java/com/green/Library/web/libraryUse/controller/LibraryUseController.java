package com.green.Library.web.libraryUse.controller;

import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class LibraryUseController {
    @Resource(name ="webMenuService")
    WebMenuService webMenuService;

    private final int selectedMenuIndex = 2;

    //    -------- 도서관이용(libraryUse)---------
    @GetMapping("/userGuide")
    public String goUserGuide(Model model){
        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드 메뉴의 인덱스 번호 보내주기
        int selectedSideMenuIndex = 1;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);






        System.out.println("이용안내 및 자료실 소개");
        return "content/homePage/libraryUse/userGuide";
    }
    @GetMapping("/eventAndCloseDay")
    public String goEventAndCloseDay(Model model){
        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        int selectedSideMenuIndex = 2;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);







        System.out.println("이달의 행사 및 휴관일");
        return "content/homePage/libraryUse/eventAndCloseDay";
    }
}
