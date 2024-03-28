package com.green.Library.web.culturalAndEducation.controller;

import com.green.Library.util.BoardUploadUtil;
import com.green.Library.util.ScheduleService;
import com.green.Library.web.board.service.BoardService;
import com.green.Library.web.board.service.BoardServiceImpl;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.PlusVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.board.vo.UploadVO;
import com.green.Library.web.culturalAndEducation.service.CulturalAndEducationServiceImpl;
import com.green.Library.web.member.service.MemberService;
import com.green.Library.web.member.service.MemberServiceImpl;
import com.green.Library.web.member.vo.ApplyVO;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class CulturalAndEducationController {
    @Resource(name ="webMenuService")
    private WebMenuService webMenuService;
    @Resource(name = "boardService")
    private BoardServiceImpl boardService;
    @Resource(name = "memberService")
    private MemberServiceImpl memberService;

    @Autowired
    private ScheduleService scheduleService;


    //    -------- 문화행사/교육(culturalAndEducation)---------
    @RequestMapping("/libraryEvent")
    public String goLibraryEvent(Model model,
                                 HttpSession session,
                                 SearchVO searchVO){
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

        //전체 게시글 수
        int totalCulBoardCnt = boardService.isNullBoardNo();

        searchVO.setTotalDataCnt(totalCulBoardCnt);
        searchVO.setPageInfo();
        if(totalCulBoardCnt == 0){
            searchVO.setEndPage(1);
        }
        searchVO.setBoardType(24);

//        boardVO.setMemberVO((MemberVO) session.getAttribute("userName"));
        List<BoardVO> boardList = boardService.selectBoardList(searchVO);
        System.out.println(boardList);

        model.addAttribute("boardList",boardList);
        System.out.println(session.getAttribute("userName"));
        System.out.println("도서관행사");

        return "content/homePage/culturalAndEducation/libraryEvent/libraryEvent";
    }

    //게시판 등록 페이지
    @GetMapping("/goEventBoard")
    public String goEventBoard(SearchVO searchVO){

        return "content/homePage/culturalAndEducation/libraryEvent/event_board";
    }


    // 문화 게시판 등록
    @PostMapping("/cultureInsertBoard")
    public String cultureInsertBoard(Model model,
                                     HttpSession session,
                                     BoardVO boardVO,
                                     @RequestParam(name = "mainFile") MultipartFile mainFile,
                                     @RequestParam(name = "subFile") MultipartFile[] subFile){
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


        //boardNo의 max값
        int maxBoardNum = boardService.isNullBoardNo();

        // 단일 이미지 첨부 기능
        UploadVO mainFileVO = BoardUploadUtil.uploadFile(mainFile);
        mainFileVO.setBoardNum(maxBoardNum);

        // 멀티 이미지 첨부 기능
        List<UploadVO> fileList = BoardUploadUtil.subImgUploadFile(subFile);
        for (UploadVO file : fileList){
            file.setBoardNum(maxBoardNum);
        }
        fileList.add(mainFileVO);
        boardVO.setFileList(fileList);
        boardVO.setBoardNum(maxBoardNum);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+session.getAttribute("userCode"));
        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        boardService.insertCulBoard(boardVO);




        return "redirect:/libraryEvent";
    }

    //도서관 행사 게시글 상세페이지
    @GetMapping("/eventDetailBoard")
    public String eventDetailBoard(Model model, BoardVO boardVO, @RequestParam(name = "boardNum") int boardNum){
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

        boardVO.setBoardNum(boardNum);

        BoardVO board = boardService.selectBoardDetail(boardVO.getBoardNum());
        boardService.boardCntUp(boardVO.getBoardNum());
        model.addAttribute("board", board);
        System.out.println(board);
        return "content/homePage/culturalAndEducation/libraryEvent/eventDetailBoard";
    }

    //문화 게시판 삭제
    @GetMapping("/cuLDeleteBoard")
    public String deleteBoard(BoardVO boardVO){
        boardService.deleteBoard(boardVO.getBoardNum());
        return "redirect:/libraryEvent";
    }

    //다중 삭제
    @GetMapping("/selectDeletes")
    public String selectDeletes(BoardVO boardVO){
        boardService.selectDeletes(boardVO);
        return "redirect:/libraryEvent";
    }



    //문화 게시판 수정
    @GetMapping("/culUpdate")
    public String culUpdate(BoardVO boardVO, Model model,HttpSession session){
        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        BoardVO board = boardService.selectBoardDetail(boardVO.getBoardNum());
        model.addAttribute("board",board);
        return "content/homePage/culturalAndEducation/libraryEvent/culBoardUpdate";

    }

    //게시판 수정 후 페이지
    @PostMapping("/culUpdateBoard")
    public String updateBoard(BoardVO boardVO){
        boardService.updateBoard(boardVO);
        return "redirect:/eventDetailBoard?boardNum="+boardVO.getBoardNum();
    }




////////////////////////////////////////////////////////////////////////////////
    //    ----------------- 행사 참가신청 -------------------
    @RequestMapping("/eventParticipation")
    public String goEventParticipation(Model model,
                                       BoardVO boardVO,
                                       SearchVO searchVO){

        //scheduleService.run();

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

        int totalCulBoardCnt = boardService.isNullBoardNo();

        searchVO.setTotalDataCnt(totalCulBoardCnt);
        searchVO.setPageInfo();
        if(totalCulBoardCnt == 0){
            searchVO.setEndPage(1);
        }
        searchVO.setBoardType(25);

        List<BoardVO> boardList =  boardService.selectPlusList(searchVO);
        model.addAttribute("boardList",boardList);
        System.out.println(boardList);
        System.out.println("행사 참가신청");
        return "content/homePage/culturalAndEducation/eventParticipation/eventParticipation";
    }

    // 참가신청 게시글 등록 페이지 이동
    @GetMapping("/goParticipation")
    public String goParticipation(){
        return "content/homePage/culturalAndEducation/eventParticipation/goParticipation";
    }

    // 참가신청 게시글 등록
    @PostMapping("/insertParticipation")
    public String insertParticipation(Model model,
                                      BoardVO boardVO,
                                      PlusVO plusVO,
                                      HttpSession session,
                                      @RequestParam(name = "files") MultipartFile[] files){

        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        webMenuService.selectWebMenuList("member");
        System.out.println(webMenuService.selectWebMenuList("member"));
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));

        //boardNo의 max값
        int maxBoardNum = boardService.isNullBoardNo();

        System.out.println(plusVO);
        System.out.println(boardVO);
        // 멀티 이미지 첨부 기능
        List<UploadVO> fileList = BoardUploadUtil.subImgUploadFile(files);
        for (UploadVO file : fileList){
            file.setBoardNum(maxBoardNum);
        }

        boardVO.setPlusVO(plusVO);
        boardVO.setFileList(fileList);
        boardVO.setBoardNum(maxBoardNum);


        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        System.out.println(boardVO);
        boardService.insertParticipation(boardVO);
        return "redirect:/eventParticipation";


    }

    // 참가 신청 상세보기 페이지 이동
    @GetMapping("/goDetailParticipation")
    public String goDetailParticipation(Model model, BoardVO boardVO,
                                        @RequestParam(name = "boardNum") int boardNum,
                                        ApplyVO applyVO,
                                        HttpSession session){
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

//        int userCode = Optional.ofNullable((Integer) session.getAttribute("userCode")).orElse(0);
//        applyVO.setUserCode(userCode);

        boardVO.setBoardNum(boardNum);
        applyVO.setUserCode((Integer) session.getAttribute("userCode"));
        model.addAttribute("check", boardService.applyCheck(applyVO));
        BoardVO board = boardService.selectEventBoardDetail(boardVO.getBoardNum());
        model.addAttribute("board", board);
        System.out.println(board);
        return "content/homePage/culturalAndEducation/eventParticipation/goDetailParticipation";
    }


    //이벤트 게시물 수정 페이지 이동
    @GetMapping("/goEventUpdatePage")
    public String goEventUpdatePage(Model model, BoardVO boardVO, HttpSession session){
        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
        webMenuService.selectWebMenuList("member");
        System.out.println(webMenuService.selectWebMenuList("member"));
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));

        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        BoardVO board = boardService.selectEventBoardDetail(boardVO.getBoardNum());
        model.addAttribute("board",board);
        System.out.println(board);
        return "content/homePage/culturalAndEducation/eventParticipation/goEventUpdatePage";
    }

    //게시글 수정
    @PostMapping("/goEventUpdate")
    public String goEventUpdate(BoardVO boardVO,PlusVO plusVO){
        System.out.println(boardVO);
        boardVO.setPlusVO(plusVO);
        boardService.updateEventBoardDetail(boardVO);
        return "redirect:/goDetailParticipation?boardNum="+boardVO.getBoardNum();
    }

    //게시글 삭제
    @GetMapping("/goEventDelete")
    public String goEventDelete(@RequestParam(name = "boardNum") int boardNum){
        boardService.eventBoardDelete(boardNum);
        return "redirect:/eventParticipation";
    }

    //게시글 선택 삭제
    @GetMapping("/goEventDeletes")
    public String goEventDeletes(BoardVO boardVO){
        boardService.selectDeletes(boardVO);
        return "redirect:/eventParticipation";
    }






    @GetMapping("/movie")
    public String goMovie(Model model){
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

        System.out.println("영화 상영");
        return "content/homePage/culturalAndEducation/movie";
    }


    ////////////////////////////////////////////////////////////////////////////////
    //    ----------------- 평생교육 강좌안내 -------------------
    @RequestMapping("/courseGuide")
    public String goCourseGuide(Model model,
                                SearchVO searchVO,
                                BoardVO boardVO){
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


        int totalCulBoardCnt = boardService.isNullBoardNo();

        searchVO.setTotalDataCnt(totalCulBoardCnt);
        searchVO.setPageInfo();
        if(totalCulBoardCnt == 0){
            searchVO.setEndPage(1);
        }
        searchVO.setBoardType(27);


        List<BoardVO> boardList =  boardService.selectBoardList(searchVO);
        System.out.println("---------------------------------------------"+searchVO);

        System.out.println(boardList);
        model.addAttribute("guideBoardList",boardList);



        System.out.println("평생교육 강좌안내");
        return "content/homePage/culturalAndEducation/courseGuide/courseGuide";
    }

    //가이드 게시판 등록 페이지 이동
    @GetMapping("/goGuideInsertPage")
    public String goGuideInsertPage(Model model){
        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
        webMenuService.selectWebMenuList("member");
        System.out.println(webMenuService.selectWebMenuList("member"));
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        return "content/homePage/culturalAndEducation/courseGuide/guideInsertPage";
    }

    //가이드 게시판 등록
    @PostMapping("/guideInsertBoard")
    public String guideInsertBoard(Model model,
                                   HttpSession session,
                                   BoardVO boardVO,
                                   @RequestParam(name = "subFile") MultipartFile[] subFile){
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


        //boardNo의 max값
        int maxBoardNum = boardService.isNullBoardNo();


        // 멀티 이미지 첨부 기능
        List<UploadVO> fileList = BoardUploadUtil.subImgUploadFile(subFile);
        for (UploadVO file : fileList){
            file.setBoardNum(maxBoardNum);
        }
        boardVO.setFileList(fileList);
        boardVO.setBoardNum(maxBoardNum);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+session.getAttribute("userCode"));
        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        boardService.insertCulBoard(boardVO);
        return "redirect:/courseGuide";
    }

    //게시판 선택 삭제
    @PostMapping("/GuideDelete")
    public String GuideDeletes(BoardVO boardVO){
        boardService.selectDeletes(boardVO);
        return "redirect:/courseGuide";
    }



    ////////////////////////////////////////////////////

    @RequestMapping("/applicationForClasses")
    public String goApplicationForClasses(Model model, SearchVO searchVO,HttpSession session, ApplyVO applyVO){
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

        int totalCulBoardCnt = boardService.isNullBoardNo();

        searchVO.setTotalDataCnt(totalCulBoardCnt);
        searchVO.setPageInfo();
        if(totalCulBoardCnt == 0){
            searchVO.setEndPage(1);
        }
        searchVO.setBoardType(28);

        int userCode = Optional.ofNullable((Integer) session.getAttribute("userCode")).orElse(0);
        applyVO.setUserCode(userCode);
        model.addAttribute("applyUser", memberService.selectApplyUser(applyVO.getUserCode()) );
        model.addAttribute("apply",boardService.applyBoardList());
        List<ApplyVO> applyList = memberService.applyList();
        List<BoardVO> boardList =  boardService.selectPlusList(searchVO);
        model.addAttribute("boardList",boardList);

        System.out.println(memberService.selectApplyUser(applyVO.getUserCode()));
        System.out.println(boardService.applyBoardList());
        System.out.println(applyList);
        System.out.println(boardList);

        System.out.println("강좌 수강신청");
        return "content/homePage/culturalAndEducation/applicationForClasses/applicationForClasses";
    }

    //수강신청 게시글 등록 페이지 이동
    @GetMapping("/goAppInsertPage")
    public String goAppInsertPage(Model model){
        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
        webMenuService.selectWebMenuList("member");
        System.out.println(webMenuService.selectWebMenuList("member"));
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        return "content/homePage/culturalAndEducation/applicationForClasses/goAppInsertPage";
    }

    //수강신청 게시글 등록 페이지
    @PostMapping("/appInsert")
    public String appInsert(BoardVO boardVO,
                            PlusVO plusVO,
                            HttpSession session,
                            @RequestParam(name = "files") MultipartFile[] files,
                            Model model){
        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
        webMenuService.selectWebMenuList("member");
        System.out.println(webMenuService.selectWebMenuList("member"));
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));


        //boardNo의 max값
        int maxBoardNum = boardService.isNullBoardNo();

        System.out.println(plusVO);
        System.out.println(boardVO);
        // 멀티 이미지 첨부 기능
        List<UploadVO> fileList = BoardUploadUtil.subImgUploadFile(files);
        for (UploadVO file : fileList){
            file.setBoardNum(maxBoardNum);
        }

        boardVO.setPlusVO(plusVO);
        boardVO.setFileList(fileList);
        boardVO.setBoardNum(maxBoardNum);

        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        System.out.println(boardVO);
        boardService.insertParticipation(boardVO);
        return "redirect:/applicationForClasses";
    }

    //신청 기록 페이지
    @GetMapping("/goApplyListPage")
    public String goApplyListPage(Model model, BoardVO boardVO, HttpSession session, MemberVO memberVO, ApplyVO applyVO){
        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
        webMenuService.selectWebMenuList("member");
        System.out.println(webMenuService.selectWebMenuList("member"));
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));

        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        System.out.println("@@@@@@@@@@@@@@@@@" + boardService.applyBoardList());
        model.addAttribute("boardList", boardService.applyBoardList());
        return "content/homePage/culturalAndEducation/applicationForClasses/goApplyList";
    }

    //회원 신청 기록 페이지
    @GetMapping("/goApplyUserListPage")
    public String goApplyUserListPage(Model model,BoardVO boardVO,HttpSession session){
        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
        webMenuService.selectWebMenuList("member");
        System.out.println(webMenuService.selectWebMenuList("member"));
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));

        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        model.addAttribute("userBoardList",memberService.applyUserBoardList(boardVO));
        return "content/homePage/culturalAndEducation/applicationForClasses/goApplyUserListPage";
    }

    //신청 취소
    @RequestMapping("/deleteApply")
    @ResponseBody
    public void deleteApply(BoardVO boardVO){
        memberService.deleteApply(boardVO.getBoardNum());
    }

    //신청 확인
    @PostMapping("/confirm")
    @ResponseBody
    public void confirm(BoardVO boardVO){
        memberService.CF(boardVO.getBoardNum());
    }
    //다중 삭제
    @GetMapping("/goAppDelete")
    public String goAppDelete(BoardVO boardVO){
        boardService.selectDeletes(boardVO);
        return "redirect:/applicationForClasses";
    }

    //인원 증가 쿼리
    @ResponseBody
    @PostMapping("/upPersonnel")
    public void upPersonnel(@RequestParam(name = "boardNum") int boardNum){
        System.out.println(boardNum);
        boardService.upPersonnel(boardNum);
    }







}
