package com.green.Library.library.statistics.controller;

import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookAdmin")
public class StatisticsController {
    @Resource(name="menuService")
    LibraryMenuService libraryMenuService;

    //---------통계-----------
    @GetMapping("/statistics")
    public String goStatistics(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("통계 이동");
        return "content/library/statistics/statistics";
    }
}
