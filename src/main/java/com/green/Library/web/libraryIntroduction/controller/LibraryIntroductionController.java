package com.green.Library.web.libraryIntroduction.controller;

import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LibraryIntroductionController {
    @Resource(name ="webMenuService")
    WebMenuService webMenuService;


    //도서관 소개의 인덱스 변호
    private final int selectedMenuIndex = 5;


    //    -------- 도서관소개(libraryintroduction)---------
    @GetMapping("/greeting")
    public String goGreeting(Model model){
        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드 메뉴의 인덱스 번호 보내주기
        int selectedSideMenuIndex = 1;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);

        System.out.println("희망도서신청");
        return "content/homePage/libraryintroduction/greeting";
    }
    @GetMapping("/history")
    public String goHistory(Model model){
        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드 메뉴의 인덱스 번호 보내주기
        int selectedSideMenuIndex = 2;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);

        System.out.println("희망도서신청");
        return "content/homePage/libraryintroduction/history";
    }
    @GetMapping("/libraryStatus")
    public String goLibraryStatus(Model model){
        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드 메뉴의 인덱스 번호 보내주기
        int selectedSideMenuIndex = 3;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);

        System.out.println("희망도서신청");
        return "content/homePage/libraryintroduction/libraryStatus";
    }
    @GetMapping("/libraryCome")
    public String goLibraryCome(Model model){
        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드 메뉴의 인덱스 번호 보내주기
        int selectedSideMenuIndex = 4;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);

        System.out.println("희망도서신청");
        return "content/homePage/libraryintroduction/libraryCome";
    }
}
