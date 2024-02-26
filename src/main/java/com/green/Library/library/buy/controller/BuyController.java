package com.green.Library.library.buy.controller;

import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookAdmin")
public class BuyController {
    @Resource(name = "menuService")
    LibraryMenuService libraryMenuService;


    //----------구입------------
    //희망 자료
    @GetMapping("/wishBook")
    public String goWishBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectMenuList());


        System.out.println("희망 자료 이동");
        return "content/library/buy/wishBook";
    }
    //삭제 자료
    @GetMapping("/deleteBook")
    public String goDeleteBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectMenuList());


        System.out.println("삭제 자료 이동");
        return "content/library/buy/deleteBook";
    }
    @GetMapping("/buyBook")
    public String goBuyBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectMenuList());


        System.out.println("구입 자료 이동");
        return "content/library/buy/buyBook";
    }
    @GetMapping("/donatedBook")
    public String goDonatedBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectMenuList());


        System.out.println("기증 자료 이동");
        return "content/library/buy/donatedBook";
    }
}
