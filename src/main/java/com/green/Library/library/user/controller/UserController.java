package com.green.Library.library.user.controller;

import com.green.Library.library.borrowReturn.service.BorrowReturnService;
import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.library.user.service.UserService;
import com.green.Library.library.user.vo.SearchUserVO;
import com.green.Library.web.member.vo.MemberVO;
import jakarta.annotation.Resource;
import org.apache.ibatis.javassist.expr.Instanceof;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/bookAdmin")

public class UserController {
    @Resource(name="menuService")
    LibraryMenuService libraryMenuService;
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "borrowReturnService")
    BorrowReturnService borrowReturnService;

    //-----------이용자------------------
    //이용자 관리
    @RequestMapping("/user")
    public String goUser(Model model, SearchUserVO searchUserVO){
        System.out.println(searchUserVO);

        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());

        // 이용자 정보 전체 조회
        List<MemberVO> membersInfo = userService.selectUserInfoList(searchUserVO);

        // cardNum이 있는 이용자를 저장 할 변수 생성
        List<MemberVO> filterIsCardNum = new ArrayList<>();

        membersInfo.forEach(m -> {
            if(m.getCardNum() != null){
                filterIsCardNum.add(m);
            }
        });

        model.addAttribute("membersInfo", filterIsCardNum);


        System.out.println(" 이동");
        return "content/library/user/user";
    }

    // 이용자 관리에서 모달에 띄울 상세정보
    @ResponseBody
    @PostMapping("/showUserDetailFetch")
    public MemberVO showUserDetail1(@RequestBody Map<String, String> userDetail){

        System.out.println("@@@@@@@@@@@@@" + userDetail);

        // 정수 변환
        int code = Integer.parseInt(userDetail.get("userCode"));

        Optional<Integer> userCode = Optional.ofNullable(code);

        System.out.println(userService.showUserDetail(userCode.get()));

        return userCode.map( u -> userService.showUserDetail(u)).orElse(null);
    }

    //이용자 승인
    @RequestMapping("/userApproval")
    public String goUserApproval(Model model, SearchUserVO searchUserVO){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());

        // 이용자 정보 전체 조회
        List<MemberVO> membersInfo = userService.selectUserInfoList(searchUserVO);

        // cardNum이 없는 이용자를 저장 할 변수 생성
        List<MemberVO> filterNoCardNum = new ArrayList<>();

        // cardNum이 0과 같은 이용자들을 필터 변수에 추가
        membersInfo.forEach(m -> {
            if(m.getCardNum() == null){
                filterNoCardNum.add(m);
            }
        });

        model.addAttribute("membersInfo", filterNoCardNum);

        System.out.println(filterNoCardNum);

        System.out.println("이용자 승인 이동");

        return "content/library/user/userApproval";
    }

    // 이용자 cardNum update
    @ResponseBody
    @PostMapping("/updateCardNumFetch")
    public int updateCardNum(@RequestParam(name = "userCodeList", required = false) List<Integer> userCodeList,
                             @RequestParam(name = "cardNum", required = false, defaultValue = "0")String cardNum){

        // cardNum 전체부여
        if(userCodeList != null){
            userCodeList.forEach(s -> userService.updateCardNum(new MemberVO().builder()
                    .userCode(s)
                    .build()));

            return 1;

        // cardNum 업데이트
        } else if(!cardNum.equals("0")){
            System.out.println("카드번호" + cardNum);

            int thisCardNum = Integer.parseInt(cardNum);

            System.out.println("!!!!!!!!!!!!!!" + thisCardNum + "!!!!!!!!!!!!!!!");

            MemberVO reGrant = new MemberVO();

            reGrant.setUserCode(borrowReturnService.selectUserCode(thisCardNum));

            userService.updateCardNum(reGrant);

            int reCardNum = userService.selectCardNum(reGrant.getUserCode());

            reGrant.setCardNum(reCardNum);

            System.out.println("reGrant" + reGrant);

            return reGrant.getCardNum();

        // 아무것도 아닌 경우
        } else {
            return 0;
        }
    }

    // 모달 상세정보 수정 업데이트
    @ResponseBody
    @RequestMapping("/updateUserDetailFetch")
    public MemberVO userDetailUpdate(@RequestBody MemberVO memberVO) {

        userService.userDetailUpdate(memberVO);

        System.out.println(memberVO);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        userService.userDetailUpdate(memberVO);

        MemberVO userInfo = new MemberVO();

        userInfo.setUserCode(memberVO.getUserCode());
        userInfo.setIsAdmin(memberVO.getIsAdmin());
        userInfo.setCardNum(memberVO.getCardNum());
        userInfo.setUserName(memberVO.getUserName());
        userInfo.setUserTel(memberVO.getUserTel());
        userInfo.setUserIntro(memberVO.getUserIntro());
        userInfo.setBorrowCnt(memberVO.getBorrowCnt());
        userInfo.setOverdueCnt(memberVO.getOverdueCnt());

        System.out.println(userInfo);

        return userInfo;

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
