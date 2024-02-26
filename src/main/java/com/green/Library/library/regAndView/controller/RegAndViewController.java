package com.green.Library.library.regAndView.controller;

import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookAdmin")
public class RegAndViewController {
    @Resource(name="menuService")
    LibraryMenuService libraryMenuService;

    //----------------등록 열람----------------
    @GetMapping("/workingBook")
    public String goWorkingBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("작업 자료 관리 이동");
        return "content/library/regAndView/workingBook";
    }
    @GetMapping("/collectionBook")
    public String goCollectionBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("소장 자료 관리 이동");
        return "content/library/regAndView/collectionBook";
    }
    @GetMapping("/markImport")
    public String goMarkImport(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("마크 반입 이동");
        return "content/library/regAndView/markImport";
    }
}
