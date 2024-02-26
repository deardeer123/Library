package com.green.Library.library.user.controller;

import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookAdmin")

public class UserController {
    @Resource(name="menuService")
    LibraryMenuService libraryMenuService;
    //-----------이용자------------------
    //이용자 관리
    @GetMapping("/user")
    public String goUser(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println(" 이동");
        return "content/library/user/user";
    }
    //이용자 승인
    @GetMapping("/userApproval")
    public String goUserApproval(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("이용자 승인 이동");
        return "content/library/user/userApproval";
    }
    //연체자 관리
    @GetMapping("/delinquent")
    public String goDelinquent(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("연체자 관리 이동");
        return "content/library/user/delinquent";
    }
}
