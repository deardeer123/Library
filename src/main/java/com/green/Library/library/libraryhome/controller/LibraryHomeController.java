package com.green.Library.library.libraryhome.controller;


import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookAdmin")
public class LibraryHomeController {
    @Resource(name="menuService")
    LibraryMenuService libraryMenuService;

    // 로그인 페이지 이동
    @GetMapping("/main")
    public String goLogin(){
        return "content/library/main";
    }

    //비동기 로그인

//    @ResponseBody
//    @PostMapping("/login")
//    public String login(@RequestBody){
//
//    }

    //홈
    @GetMapping("/home")
    public String goHome(Model model){
        System.out.println("홈으로 이동");
        System.out.println(libraryMenuService.selectMenuList());

        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectMenuList());
        return "content/library/home";
    }









}

