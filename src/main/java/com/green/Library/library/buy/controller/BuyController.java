package com.green.Library.library.buy.controller;

import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.libraryBook.service.LibraryBookService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookAdmin")
public class BuyController {
    //메뉴 불러올려고 적은거임
    @Resource(name = "menuService")
    LibraryMenuService libraryMenuService;

    //카테고리 및 뭐 책에 관련된 정보 끌고 올려고 준비햇음
    @Resource(name = "libraryBookService")
    LibraryBookService libraryBookService;



    //----------구입------------
    //희망 자료
    @GetMapping("/wishBook")
    public String goWishBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectMenuList());
        //상대방이 원하는 책을 등록하는것

        System.out.println("희망 자료 이동");
        return "content/library/buy/wishBook";
    }
    //삭제 자료
    @GetMapping("/deleteBook")
    public String goDeleteBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectMenuList());
        //그말대로 삭!제

        System.out.println("삭제 자료 이동");
        return "content/library/buy/deleteBook";
    }
    @GetMapping("/buyBook")
    public String goBuyBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectMenuList());

        //일단 넣어야할 코드 잘 가져오는지 확인좀 합시다.
        System.out.println(libraryBookService.searchMaxCode());

        //가기전에 카테고리 목록 리스트 가져오기
        //System.out.println(libraryBookService.selectCateList());
        model.addAttribute("cateList",libraryBookService.selectCateList());

        System.out.println("구입 자료 이동");
        return "content/library/buy/buyBook";
    }

    //안할거임 ㅅㄱ
    @GetMapping("/donatedBook")
    public String goDonatedBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectMenuList());


        System.out.println("기증 자료 이동");
        return "content/library/buy/donatedBook";
    }
}
