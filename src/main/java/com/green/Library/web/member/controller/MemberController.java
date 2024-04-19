package com.green.Library.web.member.controller;

import com.green.Library.web.member.service.MemberServiceImpl;
import com.green.Library.web.member.vo.ApplyVO;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;

@Controller
//@RequestMapping("/member")
@RequestMapping("/")
public class MemberController {
    @Resource(name = "memberService")
    private MemberServiceImpl memberService;

    @Resource(name ="webMenuService")
    WebMenuService webMenuService;

    //테스트 한다고 잠깐 주석처리 했어요 ㅈㅅ
//    //홈페이지 창
//    @GetMapping("/join")
//    public String join(){
//        System.out.println("홈페이지");
//        return "content/homePage/join";
//    }

    //회원 등록
    @PostMapping("/insertMember")
    public String insertMember(MemberVO memberVO){
        //문자열 치환
        memberVO.setUserTel(memberVO.getUserTel().replace(",","-"));
        memberVO.setEmail(memberVO.getEmail().replace(",",""));

        memberService.insertMember(memberVO);
        return "redirect:/login";
    }


    //회원가입으로 이동
    @GetMapping("/join")
    public String goJoin(Model model){
        model.addAttribute("page","join");

        System.out.println("회원가입");

        //로그인창으로 보내기
        return "content/homePage/member/join";
    }

    @GetMapping("/login")
    public String goLogin(Model model){
        model.addAttribute("page","login");

        System.out.println("로그인");
        return "content/homePage/member/login";
    }



    @GetMapping("/findIdOrPW")
    public String goHome(Model model){
        model.addAttribute("page","findIdOrPW");

        System.out.println("아이디/비밀번호 찾기");
        return "content/homePage/member/findIdOrPW";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginInfo");
        return "redirect:/home";
    }
    //회원 신청란
    @RequestMapping("/applyApp")
    public String apply(HttpSession session, ApplyVO applyVO , @RequestParam(name = "boardNum") int boardNum){

        applyVO.setUserCode((Integer) session.getAttribute("userCode"));
        System.out.println(session.getAttribute("userCode"));
        memberService.apply(applyVO);
        return "redirect:/goDetailParticipation?boardNum="+ boardNum;
    }


    //마이페이지 이동
    @GetMapping("/goMyPage")
    public String goMyPage(Model model,HttpSession session,MemberVO memberVO){


        model.addAttribute("page","join");

        MemberVO member = memberService.myPageUserInfo((Integer) session.getAttribute("userCode"));

        member.setEmail(member.getEmail().substring(0, member.getEmail().indexOf("@")));
        model.addAttribute("member",member);
        return "content/homePage/member/myPage";
    }

    @PostMapping("/updateUserInfo")
    public String updateUserInfo(MemberVO memberVO){
        memberVO.setEmail(memberVO.getEmail().replace(",", ""));
        memberService.updateUserInfo(memberVO);
        return "redirect:/goMyPage";
    }




}
