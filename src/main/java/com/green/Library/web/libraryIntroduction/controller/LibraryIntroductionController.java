package com.green.Library.web.libraryIntroduction.controller;

import com.green.Library.web.libraryIntroduction.service.LibraryIntroductionService;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class LibraryIntroductionController {
    @Resource(name ="webMenuService")
    WebMenuService webMenuService;

    @Resource(name="libraryIntroductionService")
    private LibraryIntroductionService libraryIntroductionService;


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
        //인터셉터에 newBook 정보를 넘겨줌
        model.addAttribute("page","libraryStatus");

        //오늘 날짜 구하기
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        System.out.println(sdf.format(now));
        model.addAttribute("date", sdf.format(now) );

        //카테고리별 책 갯수
        List<Map<String, Object>> mapList = libraryIntroductionService.libraryState();
        mapList.forEach(s-> System.out.println(s));
        //총 책 갯수
        int bookCount = libraryIntroductionService.libraryState2();
        //보내주기
        model.addAttribute("cateCountList", mapList);
        model.addAttribute("totalCount", bookCount);



        System.out.println("도서관 상태");
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
