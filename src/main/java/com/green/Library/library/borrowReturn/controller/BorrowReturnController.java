package com.green.Library.library.borrowReturn.controller;

import com.green.Library.library.borrowReturn.service.BorrowReturnService;
import com.green.Library.library.borrowReturn.vo.BookBorrowVO;
import com.green.Library.library.borrowReturn.vo.MemberCardVO;
import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.web.member.vo.MemberVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookAdmin")
public class BorrowReturnController {
    @Resource(name = "menuService")
    LibraryMenuService libraryMenuService;
    @Resource(name = "borrowReturnService")
    BorrowReturnService borrowReturnService;


    //---------------대출반납----------------------------------------
    //대출 반납
    @GetMapping("/borrowReturn")
    public String goBorrowReturn(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());

        System.out.println("대출반납으로 이동");
        return "content/library/borrowReturn/borrowReturn";
    }

    //이용자 대출 반납 조회
    @ResponseBody
    @PostMapping("/selectBorrowInfo")
    public MemberCardVO selectBorrowInfo(MemberCardVO memberCardVO, int userCode){

        MemberCardVO memberBorrowInfo = borrowReturnService.selectBorrowInfo(userCode);

        System.out.println(memberBorrowInfo);

        return memberBorrowInfo;
    }

    //일관 반납
    @GetMapping("/consistentReturn")
    public String goConsistentReturn(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("대출반납으로 이동");
        return "content/library/borrowReturn/consistentReturn";
    }

    //대출 반납 관리
    @GetMapping("/borrowReturnManagement")
    public String goBorrowReturnManagement(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("대출 반납 관리 이동");
        return "content/library/borrowReturn/borrowReturnManagement";
    }

    //예약 정보 관리
    @GetMapping("/reservationInfo")
    public String goReservationInfo(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("예약 정보 관리 이동");
        return "content/library/borrowReturn/reservationInfo";
    }

    //출력 이력 관리
    @GetMapping("/outputHistory")
    public String goOutputHistory(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("출력 이력 관리 이동");
        return "content/library/borrowReturn/outputHistory";
    }


}
