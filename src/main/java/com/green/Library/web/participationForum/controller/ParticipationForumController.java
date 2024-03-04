package com.green.Library.web.participationForum.controller;

import com.green.Library.web.board.service.BoardServiceImpl;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.participationForum.service.ParticipationForumServiceIMPL;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import com.green.Library.web.webMenu.service.WebMenuService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/")
public class ParticipationForumController {
    @Resource(name ="webMenuService")
    WebMenuService webMenuService;
    @Resource(name = "ParticipationForumService")
    private ParticipationForumServiceIMPL participationForumService;
    @Resource(name = "boardService")
    private BoardServiceImpl boardService;

    //    -------- 참여마당(forum)---------

    //공지사항조회
    @GetMapping("/notice")
    public String goNotice(BoardVO boardVO, Model model, HttpSession session, SearchVO searchVO){
        //드가기전 메뉴 정보좀 들고옴
        //제대로 들고가는지 확인
        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
        webMenuService.selectWebMenuList("member");
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));

        System.out.println("공지사항");

        //글목록 조회
        List<BoardVO> noticeList = participationForumService.selectNotice();
        model.addAttribute("noticeList", noticeList);

        //페이징처리
        int totalBoardCnt= boardService.countBoard();
        searchVO.setTotalDataCnt(totalBoardCnt);

        searchVO.setPageInfo();

        List<BoardVO> boardList = boardService.selectBoardList(searchVO);
        System.out.println(boardList);

        return "content/homePage/forum/notice";
    }
    //공지사항 글쓰기 페이지이동
    @GetMapping("/noticeWrite")
    public String noticeWrite (){
        return "content/homePage/forum/noticeWrite";
    }

    //공지사항 글쓰기
    @PostMapping("/noticeWrite")
    public String noticeWrite(BoardVO boardVO, HttpSession session, Model model
                              ){

        //로그인 정보 전달
        MemberVO loginInfo = (MemberVO) session.getAttribute("loginInfo");
        boardVO.setUserCode(loginInfo.getUserCode());
        model.addAttribute("loginInfo",loginInfo);

        //글등록
        participationForumService.insertNotice(boardVO);

        //첨부파일기능



        return "redirect:/notice";
    }
    //공지사항 글 상세조회
    @GetMapping("/noticeDetail")
    public String noticeDetail(@RequestParam(name = "boardNo") int boardNo, Model model){

        //공지 상세조회
        participationForumService.noticeDetail(boardNo);

        //조회수 증가
        participationForumService.updateCnt(boardNo);

        BoardVO noticeDetail = participationForumService.noticeDetail(boardNo);
        model.addAttribute("notice", noticeDetail);

        return "content/homePage/forum/noticeDetail";
    }

    //묻고답하기
    @GetMapping("/askAndAnswer")
    public String goAskAndAnswer(Model model){
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

        System.out.println("묻고답하기");
        return "content/homePage/forum/askAndAnswer";
    }
    @GetMapping("/bookDonation")
    public String goBookDonation(Model model){
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

        System.out.println("자료기증");
        return "content/homePage/forum/bookDonation";
    }
    @GetMapping("/lockerReservation")
    public String goLockerReservation(Model model){
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

        System.out.println("사물함예약");
        return "content/homePage/forum/lockerReservation";

    }

}
