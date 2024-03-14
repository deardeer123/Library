package com.green.Library.web.findBook.controller;

import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.web.findBook.service.FindBookService;
import com.green.Library.web.findBook.vo.FindBookVO;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class FindBookController {
    @Resource(name ="webMenuService")
    WebMenuService webMenuService;

    @Resource(name= "findBookService")
    FindBookService findBookService;

    private final int selectedMenuIndex = 1;


    @RequestMapping("/findFullBook")
    public String goFindFullBook(Model model, BookSearchVO bookSearchVO){
        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //상단 네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드 메뉴의 인덱스 번호 보내주기
        int selectedSideMenuIndex = 1;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);


        //페이징
        System.out.println(bookSearchVO.getNowPage());
        bookSearchVO.setNowPage(bookSearchVO.getNowPage());

        //전체 게시물 갯수 설정
        int totalDataCnt = findBookService.selectFindBookCnt(bookSearchVO);
        System.out.println(totalDataCnt);
        bookSearchVO.setTotalDataCnt(totalDataCnt);

        //페이지 정보 세팅
        bookSearchVO.setPageInfo();

        if(totalDataCnt == 0){
            bookSearchVO.setEndPage(1);
        }

        //책 리스트 보내기
        model.addAttribute("bookList", findBookService.findBookList(bookSearchVO));


        System.out.println("전체자료찾기");

        return "content/homePage/findBook/findFullBook";
    }

    
    //책 하나 정보 얻기
    @ResponseBody
    @PostMapping("/findBookDetail")
    public FindBookVO findBookDetail(@RequestParam(name = "bookCode") String bookCode){
        FindBookVO findBookVO = findBookService.findBookOne(bookCode);
        System.out.println(findBookVO);
        return findBookVO;
    }

//    ------------------------------------------------------------------------------------------------
    @GetMapping("/newBook")
    public String goNewBook(Model model){

        //헤더 메뉴
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //로그인 네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드 메뉴의 인덱스 번호 보내주기
        int selectedSideMenuIndex = 2;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);


        System.out.println("새로 들어온 책");
        return "content/homePage/findBook/newBook";
    }
    @GetMapping("/recommendedBook")
    public String goRecommendedBook(Model model){
        //헤더 메뉴
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //로그인 네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드 메뉴의 인덱스 번호 보내주기
        int selectedSideMenuIndex = 3;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);




        System.out.println("추천도서");
        return "content/homePage/findBook/recommendedBook";
    }
    @GetMapping("/manyBorrowedBook")
    public String goManyBorrowedBook(Model model){
        //헤더 메뉴
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //로그인 네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드 메뉴의 인덱스 번호 보내주기
        int selectedSideMenuIndex = 4;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);



        System.out.println("대출이 많은책");
        return "content/homePage/findBook/manyBorrowedBook";
    }
    @GetMapping("/hopeBookApplication")
    public String goHopeBookApplication(Model model){
        //헤더 메뉴
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //로그인 네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드 메뉴의 인덱스 번호 보내주기
        int selectedSideMenuIndex = 5;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);



        System.out.println("희망도서신청");
        return "content/homePage/findBook/hopeBookApplication";
    }
}
