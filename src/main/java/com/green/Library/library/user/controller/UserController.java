package com.green.Library.library.user.controller;

import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.library.user.service.UserService;
import com.green.Library.library.user.vo.SearchUserVO;
import com.green.Library.library.user.vo.UserVO;
import com.green.Library.web.member.vo.MemberVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bookAdmin")

public class UserController {
    @Resource(name="menuService")
    LibraryMenuService libraryMenuService;
    @Resource(name = "userService")
    private UserService userService;

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

    // 이용자 승인 페이지 조회(cardNum 부여 하기 위해 우선 select)
    @PostMapping("/letUserApproval")
    public String letUserApproval(Model model, SearchUserVO searchUserVO){

        // 이용자 정보 전체 조회
        List<MemberVO> membersInfo = userService.selectUserInfoList(searchUserVO);

        // cardNum이 없는 이용자를 저장 할 변수 생성
        List<MemberVO> filterNoCardNum = new ArrayList<>();

        // cardNum이 0과 같은 이용자들을 필터 변수에 추가
        for(MemberVO memberVO : membersInfo){
            if(memberVO.getCardNum() == 0){
                filterNoCardNum.add(memberVO);
            }
        }

        model.addAttribute("membersInfo", filterNoCardNum);

        System.out.println(filterNoCardNum);

        return "redirect:/bookAdmin/userApproval";
    }

    // 이용자 cardNum update
    @PostMapping("/updateCardNum")
    public String updateCardNum(MemberVO memberVO){

        // cardNum 업데이트 쿼리
        userService.updateCardNum(memberVO);

        return "redirect:/bookAdmin/userApproval";
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
