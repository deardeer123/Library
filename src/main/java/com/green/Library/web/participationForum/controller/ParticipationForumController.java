package com.green.Library.web.participationForum.controller;

import com.green.Library.util.BoardUploadUtil;
import com.green.Library.web.board.service.BoardServiceImpl;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.board.vo.UploadVO;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.participationForum.service.ParticipationForumServiceIMPL;
import com.green.Library.web.participationForum.vo.AskAndAnswerBoardVO;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ParticipationForumController {
    @Resource(name ="webMenuService")
    WebMenuService webMenuService;
    @Resource(name = "ParticipationForumService")
    private ParticipationForumServiceIMPL participationForumService;
    @Resource(name = "boardService")
    private BoardServiceImpl boardService;


    private final int selectedMenuIndex = 4;


    //    -------- 참여마당(forum)---------

    //공지사항조회
    @RequestMapping("/notice")
    public String goNotice(Model model, SearchVO searchVO){
        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //상단 네비게이션 정보

        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드메뉴 인덱스 번호 보내주기
        int selectedSideMenuIndex = 1;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);

        System.out.println("공지사항");

//        //전체데이터수
//        int totalBoardCnt= participationForumService.partiCountBoard(searchVO);
//        searchVO.setTotalDataCnt(totalBoardCnt);
//
//        //페이지정보세팅
//        searchVO.setPageInfo();
//
//        //글목록 조회
//        model.addAttribute("noticeList", participationForumService.forumSelectBoardList(searchVO));



        return "content/homePage/forum/notice";
    }


    //공지사항 글쓰기 페이지이동
    @GetMapping("/noticeWrite")
    public String noticeWrite (Model model){
        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //상단 네비게이션 정보
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));


        return "content/homePage/forum/noticeWrite2";
    }

    //공지사항 글쓰기
    @PostMapping("/noticeWrite")
    public String noticeWrite(BoardVO boardVO, HttpSession session, Model model,
                              @RequestParam(name = "files") MultipartFile[] files){
//        //로그인 정보 전달
//        MemberVO loginInfo = (MemberVO) session.getAttribute("loginInfo");
//        boardVO.setUserCode(loginInfo.getUserCode());
//        model.addAttribute("loginInfo",loginInfo);
//
//        //게시글 다음 최대값 조회
//        int boardNo = participationForumService.selectNextBoardCode();
//        boardVO.setBoardNum(boardNo);
//
//        //첨부파일등록
//        List<UploadVO> fileList = BoardUploadUtil.subImgUploadFile(files);
//        for (UploadVO file : fileList){
//            file.setBoardNum(boardNo);
//        }
//        boardVO.setFileList(fileList);
//
//        //글등록
//        participationForumService.insertNotice(boardVO);

        return "redirect:/notice";
    }
    //공지사항 글 상세조회
    @GetMapping("/noticeDetail")
    public String noticeDetail(@RequestParam(name = "boardNo") int boardNo, Model model){
//        //조회수 증가
//        participationForumService.updateCnt(boardNo);
//
//        //공지 상세조회
//        BoardVO noticeDetail = participationForumService.noticeDetail(boardNo);
//        model.addAttribute("noticeDetail", noticeDetail);
//
//        System.out.println("************************************" + noticeDetail);

        return "content/homePage/forum/noticeDetail";
    }

    //묻고답하기
    @GetMapping("/askAndAnswer")
    public String goAskAndAnswer(Model model, SearchVO searchVO){
        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드메뉴 인덱스 번호 보내주기
        int selectedSideMenuIndex = 2;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);

        System.out.println(searchVO.getBoardType());
        System.out.println("묻고답하기");
        //총 게시판 갯수
        int boardCnt = participationForumService.partiCountBoard(searchVO);
        //페이징
        searchVO.setTotalDataCnt(boardCnt);
        searchVO.setPageInfo();

        //페이지 정보 세팅
        searchVO.setPageInfo();

        //계속 이상하게 나오길래 넣은 코드입니다.
        if(boardCnt == 0){
            searchVO.setEndPage(1);
        }
        //묻고 답하기 글 들고오기
        List<BoardVO> boardVOList = participationForumService.selectAskAndAnswerBoardList(searchVO);
        System.out.println(boardVOList);
        System.out.println("시발");
        model.addAttribute("boardVOList", boardVOList);

//        //전체데이터수
//        int totalBoardCnt= participationForumService.partiCountBoard(searchVO);
//        searchVO.setTotalDataCnt(totalBoardCnt);
//
//        //페이지정보세팅
//        searchVO.setPageInfo();
//
//        //글목록 조회
//        model.addAttribute("qnaList", participationForumService.forumSelectBoardList(searchVO));

        return "content/homePage/forum/askAndAnswer";
    }

    @GetMapping("/askAndAnswerWrite")
    public String writeAskAndAnswer(Model model, SearchVO searchVO){
//        묻고 답하기 글 작성 페이지 이동

        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드메뉴 인덱스 번호 보내주기
        int selectedSideMenuIndex = 2;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);




        return "content/homePage/forum/askAndAnswerWrite";
    }

    @PostMapping("/askAndAnswerWrite2")
    public String askAndAnswerWrite2(BoardVO boardVO, UploadVO uploadVO, AskAndAnswerBoardVO askAndAnswerBoardVO ,
            MemberVO memberVO ,
            @RequestParam(name="file")MultipartFile file,
            @RequestParam(name="boardType") int boardType){


        //게시판 번호 구하기
        int boardNum = boardService.isNullBoardNo();
        //게시판 타입 넣기
        boardVO.setBoardType(boardType);

        //게시판 번호 넣기
        boardVO.setBoardNum(boardNum);
        askAndAnswerBoardVO.setBoardNum(boardNum);

        //첨부파일 넣기
        uploadVO = BoardUploadUtil.uploadFile(file);

        //옵셔널 위에 받은 uploadVO 값이 null 값이 아닌경우 아래의 코드가 실행됩니다.
        //널값이 아닌 경우에는 uploadVO1에 uploadVO를 넣고 아닌경우에는 새로운 객체를 생성해줍니다
        UploadVO uploadVO1 = Optional.ofNullable(uploadVO).orElse(
                new UploadVO().builder()
                        .AttachedFileName("")
                        .OriginFileName("")
                        .isMain("N")
                        .build());
        //첨부파일 게시판 번호 넣어주기
        uploadVO1.setBoardNum(boardNum);

        List<UploadVO> fileList = new ArrayList<>();
        fileList.add(uploadVO1);

        //boadrdVO에 fileList 넣어주기
        boardVO.setFileList(fileList);

        //boardVO에 askAndAnswerBoardVO 넣기
        boardVO.setAskAndAnswerBoardVO(askAndAnswerBoardVO);

        //memberVO도 넣어줘야 하는데 일단 가짜 데이터 홍길동 넣음
        boardVO.setMemberVO(memberVO);

        //확인
        System.out.println(boardVO);

        participationForumService.insertAskAndAnswerBoard(boardVO);

        return "redirect:/askAndAnswer";
    }

    @GetMapping("/goDetailAskBoard")
    public String goDetailAskBoard(Model model, @RequestParam(name="boardNum")int boardNum){
        //묻고 답하기 게시물 보기

        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드메뉴 인덱스 번호 보내주기
        int selectedSideMenuIndex = 2;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);

        BoardVO boardVO = participationForumService.detailAskBoard(boardNum);
        model.addAttribute("boardVO",boardVO);
        return "content/homePage/forum/detailAskBoard";
    }


    @GetMapping("/bookDonation")
    public String goBookDonation(Model model){
        ///메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드메뉴 인덱스 번호 보내주기
        int selectedSideMenuIndex = 3;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);




        System.out.println("자료기증");
        return "content/homePage/forum/bookDonation";
    }
    @GetMapping("/lockerReservation")
    public String goLockerReservation(Model model){
        //메뉴 정보
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //네비게이션
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //선택한 메뉴의 인덱스 번호 보내주기
        model.addAttribute("selectedMenuIndex", selectedMenuIndex);
        //선택한 사이드메뉴 인덱스 번호 보내주기
        int selectedSideMenuIndex = 4;
        model.addAttribute("selectedSideMenuIndex", selectedSideMenuIndex);



        System.out.println("사물함예약");
        return "content/homePage/forum/lockerReservation";

    }

}
