package com.green.Library.web.member.controller;

import com.green.Library.web.member.service.MemberServiceImpl;
import com.green.Library.web.member.vo.MemberVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.Name;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Resource(name = "memberService")
    private MemberServiceImpl memberService;

    //홈페이지 창
    @GetMapping("/join")
    public String join(){
        System.out.println("홈페이지");
        return "content/homePage/join";
    }

    //회원가입
    @PostMapping("/insertMember")
    public String insertMember(MemberVO memberVO){
        //문자열 치환
        memberVO.setUserTel(memberVO.getUserTel().replace(",","-"));
        memberVO.setEmail(memberVO.getEmail().replace(",",""));

        memberService.insertMember(memberVO);
        return "redirect:/webHome/home";
    }




}
