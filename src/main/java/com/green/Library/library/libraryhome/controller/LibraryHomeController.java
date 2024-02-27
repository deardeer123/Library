package com.green.Library.library.libraryhome.controller;


import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.library.libraryhome.service.LibraryHomeService;
import com.green.Library.library.libraryhome.service.LibraryHomeServiceImpl;
import com.green.Library.libraryMember.vo.LibraryMemberVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.Encoder;

@Controller
@RequestMapping("/bookAdmin")
public class LibraryHomeController {
    @Resource(name="menuService")
    LibraryMenuService libraryMenuService;

    @Resource(name = "libraryHomeService")
    private LibraryHomeService libraryHomeService;

    // 관리자 로그인 페이지 이동
    @GetMapping("/main")
    public String goLogin(){
        return "content/library/main";
    }

    // 관리자 로그인
    @PostMapping("/login")
    public String login(LibraryMemberVO libraryMemberVO, HttpSession session){

        LibraryMemberVO loginInfo = libraryHomeService.login(libraryMemberVO);

        System.out.println(loginInfo);

        if(loginInfo != null){
            session.setAttribute("loginInfo", loginInfo);
        }

        return "redirect:/bookAdmin/home";
    }

    //홈
    @GetMapping("/home")
    public String goHome(Model model, HttpSession session){
        System.out.println("홈으로 이동");
        System.out.println(libraryMenuService.selectLibraryMenuList());

        model.addAttribute("loginInfo", session.getAttribute("loginInfo"));

        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());
        return "content/library/home";
    }









}

