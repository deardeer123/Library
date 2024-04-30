package com.green.Library.web.member.controller;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.member.service.MemberServiceImpl;
import com.green.Library.web.member.vo.ApplyVO;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Name;
import java.beans.Encoder;
import java.util.Map;

@Controller
//@RequestMapping("/member")
@RequestMapping("/")
public class MemberController {
    @Resource(name = "memberService")
    private MemberServiceImpl memberService;

    @Resource(name ="webMenuService")
    WebMenuService webMenuService;

    @Autowired
    private BCryptPasswordEncoder encoder;

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
        //비밀번호 암호화
        String memberPw = encoder.encode(memberVO.getUserPw());
        memberVO.setUserPw(memberPw);
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
    public String goLogin(Model model, @RequestParam(name="errMsg", defaultValue = "", required = false)String errMsg){
        model.addAttribute("page","login");
        //로그인 실패시 보낼 문자열
        model.addAttribute("errMsg", errMsg);

        System.out.println("로그인");
        return "content/homePage/member/login";
    }



    @GetMapping("/findIdOrPW")
    public String goHome(Model model){
        model.addAttribute("page","findIdOrPW");

        System.out.println("아이디/비밀번호 찾기");
        return "content/homePage/member/findIdOrPW";
    }

//    @PostMapping("/findId")
//    public void findId(){
//
//    }


    @PostMapping("/findPw")
    public String findPw(MemberVO memberVO){
        //임시 비밀번호 생성
        String randomPw = memberService.createRandomPw();

        //암호화
        String encodedPw = encoder.encode(randomPw);
        //vo에 담고
        memberVO.setUserPw(encodedPw);
        //암호화된 임시비밀번호를 업데이트
        memberService.updateUserPw(memberVO);
        return "";
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


    //회원 정보 변경
    @GetMapping("memberInfoChange")
    public String memberInfoChange(Model model){

        return "redirect:/goMyPage";
    }

    //마이페이지 이동
    @GetMapping("/goMyPage")
    public String goMyPage(Model model,HttpSession session,MemberVO memberVO){

        //회원 정보 변경 메뉴
        model.addAttribute("page","memberInfoChange");

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

    @GetMapping("passwdChange")
    public String passwdChange(Model model, HttpSession session)    {

        //비빌번호 변경
        model.addAttribute("page","passwdChange");
        MemberVO member = memberService.myPageUserInfo((Integer) session.getAttribute("userCode"));
        model.addAttribute("member",member);
        System.out.println(member);
        return "content/homePage/member/passwdChange";
    }


    @ResponseBody
    @PostMapping("/changePwFetch")
    public String changePw(@RequestParam(name = "userPw") String userPw,
                         @RequestParam(name = "userCode") int userCode,
                         @RequestParam(name = "newPw") String newPw,
                         MemberVO memberVO,HttpSession session){

        System.out.println(userCode);
        MemberVO memberVO1 = memberService.myPageUserInfo(userCode);

        System.out.println("인코더비번"+encoder.encode(userPw));
        System.out.println("userPw" + userPw);
        System.out.println("memberVO"+memberVO1.getUserPw());

        System.out.println("매치"+encoder.matches(userPw, memberVO1.getUserPw()));
        String str = "";
        if(!encoder.matches(userPw, memberVO1.getUserPw())){
            str += "비밀번호를 확인해주세요";
        }else{
            str += "비밀번호가 변경 되었습니다.";
            memberVO.setUserPw(encoder.encode(newPw));
            memberVO.setUserCode(userCode);
            memberService.updateUserPw(memberVO);
        }
        return str;
    }



    @GetMapping("applyList")
    public String applyList(Model model, BoardVO boardVO, HttpSession session){
        model.addAttribute("page","applyList");
        //신청 목록
        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        model.addAttribute("userBoardList",memberService.applyUserBoardList(boardVO));
        System.out.println(memberService.applyUserBoardList(boardVO));
        return "content/homePage/member/applyList";
    }


    @GetMapping("bookLoanReturn")
    public String bookLoanReturn(Model model){
        //도서 대출 반납 목록
        model.addAttribute("page", "bookLoanReturn");

        return "content/homePage/member/bookLoanReturn";
    }

    @GetMapping("myBookingList")
    public String myBookingList(Model model, HttpSession session){
        //내 (도서)예약 목록
        model.addAttribute("page", "myBookingList");

        return "content/homePage/member/myBookingList?" + session.getAttribute("userCode");
    }

    @GetMapping("memberWithdrawal")
    public String memberWithdrawal(Model model,HttpSession session){
        //회원 탈퇴

        model.addAttribute("page", "memberWithdrawal");
        return "content/homePage/member/memberWithdrawal";
    }



}
