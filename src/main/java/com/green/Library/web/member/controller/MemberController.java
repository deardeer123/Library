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
        //드가기전 메뉴 정보좀 들고옴
        //제대로 들고가는지 확인
        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
        webMenuService.selectWebMenuList("member");
        System.out.println(webMenuService.selectWebMenuList("member"));
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));

        System.out.println("로그인");
        return "content/homePage/member/login";
    }

//    //로그인
//    @PostMapping("/loginForm")
//    public String login(HttpSession session, MemberVO memberVO, Model model){
//        //드가기전 메뉴 정보좀 들고옴
//        //제대로 들고가는지 확인
//        System.out.println(webMenuService.selectWebMenuList("web"));
//        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
//
//        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
//        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
//        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
//        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
//        webMenuService.selectWebMenuList("member");
//        System.out.println(webMenuService.selectWebMenuList("member"));
//        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
//
//
//        MemberVO loginInfo = memberService.login(memberVO);
//
//        if(loginInfo != null){
//            session.setAttribute("loginInfo",loginInfo);
//            return "redirect:/home";
//        }
//        return "content/homepage/member/login_result";
//    }


    @GetMapping("/findIdOrPW")
    public String goHome(Model model){
        //드가기전 메뉴 정보좀 들고옴
        //제대로 들고가는지 확인
        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
        webMenuService.selectWebMenuList("member");
        System.out.println(webMenuService.selectWebMenuList("member"));
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));

        System.out.println("아이디/비밀번호 찾기");
        return "content/homePage/member/findIdOrPW";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginInfo");
        return "redirect:/home";
    }

    @RequestMapping("/applyApp")
    public String apply(HttpSession session, ApplyVO applyVO , @RequestParam(name = "boardNum") int boardNum){
        applyVO.setUserCode((Integer) session.getAttribute("userCode"));
        System.out.println(session.getAttribute("userCode"));
        memberService.apply(applyVO);
        return "redirect:/goDetailParticipation?boardNum="+ boardNum;
    }




}
