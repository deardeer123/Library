package com.green.Library.web.culturalAndEducation.controller;

import com.green.Library.util.BoardUploadUtil;
import com.green.Library.util.UploadUtil;
import com.green.Library.web.board.service.BoardService;
import com.green.Library.web.board.service.BoardServiceImpl;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.board.vo.UploadVO;
import com.green.Library.web.culturalAndEducation.service.CulturalAndEducationServiceImpl;
import com.green.Library.web.culturalAndEducation.vo.CulturalAndEducationVO;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/")
public class CulturalAndEducationController {
    @Resource(name ="webMenuService")
    WebMenuService webMenuService;
    @Resource(name = "boardService")
    BoardServiceImpl boardService;
    @Resource(name = "culturalAndEducationService")
    CulturalAndEducationServiceImpl cultureService;


    //    -------- 문화행사/교육(culturalAndEducation)---------
    @RequestMapping("/libraryEvent")
    public String goLibraryEvent(Model model,
                                 CulturalAndEducationVO culturalAndEducationVO,
                                 HttpSession session,
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

        //전체 게시글 수
        int totalCulBoardCnt = cultureService.culCountBoard();
        searchVO.setTotalDataCnt(totalCulBoardCnt);
        boardVO.setBoardType(21);
        searchVO.setPageInfo();
        searchVO.setBoardType(boardVO.getBoardType());
        List<BoardVO> boardList = cultureService.culSelectBoardList(searchVO);
        System.out.println(boardList);

        model.addAttribute("boardList",boardList);

        System.out.println("도서관행사");

        return "content/homePage/culturalAndEducation/libraryEvent";
    }

    //게시판 등록 페이지
    @GetMapping("/goEventBoard")
    public String goEventBoard(){
        return "content/homePage/culturalAndEducation/event_board";
    }


    // 문화 게시판 등록
    @PostMapping("/cultureInsertBoard")
    public String cultureInsertBoard(Model model,
                                     BoardVO boardVO,
                                     MemberVO memberVO,
                                     HttpSession session,
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


        MemberVO loginInfo = (MemberVO)session.getAttribute("loginInfo");

        //boardNo의 max값
        int maxBoardNo = cultureService.culIsNullBoardNo();

        // 단일 이미지 첨부 기능
        UploadVO mainFileVO = BoardUploadUtil.uploadFile(mainFile);
        mainFileVO.setBoardNo(maxBoardNo);

        // 멀티 이미지 첨부 기능
        List<UploadVO> fileList = BoardUploadUtil.subImgUploadFile(subFile);
        for (UploadVO img : fileList){
            img.setBoardNo(maxBoardNo);
        }
        fileList.add(mainFileVO);
        boardVO.setFileList(fileList);
        System.out.println(boardVO);

        boardVO.setBoardNo(maxBoardNo);
        System.out.println(boardVO);
        cultureService.culInsertBoard(boardVO);
        memberVO.setUserCode(loginInfo.getUserCode());


        return "redirect:/libraryEvent";
    }

    //도서관 행사 게시글 상세페이지
    @GetMapping("/eventDetailBoard")
    public String eventDetailBoard(Model model, BoardVO boardVO){
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


        BoardVO board = cultureService.culSelectBoardDetail(boardVO.getBoardNo());
        cultureService.culBoardCntUp(boardVO.getBoardNo());
        model.addAttribute("board", board);
        System.out.println(board);
        return "content/homePage/culturalAndEducation/eventDetailBoard";
    }

    //문화 게시판 삭제
    @GetMapping("/cuLDeleteBoard")
    public String deleteBoard(BoardVO boardVO){
        cultureService.culDeleteBoard(boardVO.getBoardNo());
        return "redirect:/libraryEvent";
    }

    //문화 게시판 수정
    @GetMapping("/culUpdate")
    public String culUpdate(BoardVO boardVO, Model model){
        BoardVO board = boardService.selectBoardDetail(boardVO.getBoardNo());
        model.addAttribute("board",board);
        return "content/homePage/culturalAndEducation/culBoardUpdate";

    }

    //게시판 수정 후 페이지
    @PostMapping("/culUpdateBoard")
    public String updateBoard(BoardVO boardVO){
        boardService.updateBoard(boardVO);
        return "redirect:/eventDetailBoard?boardNo="+boardVO.getBoardNo();
    }



////////////////////////////////////////////////////////////////////////////////
    //    ----------------- 행사 참가신청 -------------------
    @GetMapping("/eventParticipation")
    public String goEventParticipation(Model model,
                                       BoardVO boardVO,
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

        int totalBoardCnt= boardService.countBoard();
        searchVO.setTotalDataCnt(totalBoardCnt);

        searchVO.setPageInfo();
        List<BoardVO> boardList =  boardService.selectBoardList(searchVO);
        model.addAttribute("boardList",boardList);
        System.out.println(boardList);
        System.out.println("행사 참가신청");
        return "content/homePage/culturalAndEducation/eventParticipation";
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
    @GetMapping("/courseGuide")
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

        int totalBoardCnt= cultureService.culCountBoard();
        searchVO.setTotalDataCnt(totalBoardCnt);
        boardVO.setBoardType(24);
        searchVO.setPageInfo();
        searchVO.setBoardType(boardVO.getBoardType());


        List<BoardVO> boardList =  cultureService.culSelectBoardList(searchVO);
        System.out.println("---------------------------------------------"+searchVO);
        model.addAttribute("guideBoardList",boardList);




        System.out.println("평생교육 강좌안내");
        return "content/homePage/culturalAndEducation/courseGuide";
    }

    @GetMapping("/goGuideInsertPage")
    public String goGuideInsertPage(){
        return "content/homePage/culturalAndEducation/guideInsertPage";
    }

    @PostMapping("/guideInsertBoard")
    public String guideInsertBoard(Model model,
                                     BoardVO boardVO,
                                     MemberVO memberVO,
                                     HttpSession session,
                                     @RequestParam(name = "mainFile") MultipartFile mainFile,
                                     @RequestParam(name = "subFiles") MultipartFile[] subFiles) {
        //드가기전 메뉴 정보좀 들고옴
        //제대로 들고가는지 확인
        System.out.println(webMenuService.selectWebMenuList("web"));
        model.addAttribute("menuList", webMenuService.selectWebMenuList("web"));

        //만약에 세션으로 회원정보가 있을 경우에는 헤더 부분에 다르게 표현할 경우가 있음
        //로그인을 했으면 로그인, 회원가입, 아이디/비밀번호 찾기가 보일 필요가 없음
        //조건문으로 세션값(로그인했다 안했다)이 있다 없다 확인해서 있는 경우에는 딴거 표시하고
        //없는 경우에는 아래의 서비스를 통해서 메뉴(로그인, 회원가입 , 아이디/비밀번호 이 표시되도록 해야함)
        webMenuService.selectWebMenuList("member");
        System.out.println(webMenuService.selectWebMenuList("member"));
        model.addAttribute("memberMenuList", webMenuService.selectWebMenuList("member"));

        MemberVO loginInfo = (MemberVO) session.getAttribute("loginInfo");

        //boardNo의 max값
        int maxBoardNo = cultureService.culIsNullBoardNo();

        // 단일 이미지 첨부 기능
        UploadVO mainFileVO = BoardUploadUtil.uploadFile(mainFile);
        mainFileVO.setBoardNo(maxBoardNo);

        // 멀티 이미지 첨부 기능
        List<UploadVO> fileList = BoardUploadUtil.subImgUploadFile(subFiles);
        for (UploadVO file : fileList) {
            file.setBoardNo(maxBoardNo);
        }
        fileList.add(mainFileVO);
        boardVO.setFileList(fileList);
        System.out.println(boardVO);

        boardVO.setBoardNo(maxBoardNo);
        System.out.println(boardVO);
        cultureService.culInsertBoard(boardVO);
        memberVO.setUserCode(loginInfo.getUserCode());


        return "redirect:/courseGuide";
    }

    @GetMapping("/applicationForClasses")
    public String goApplicationForClasses(Model model){
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

        System.out.println("강좌 수강신청");
        return "content/homePage/culturalAndEducation/applicationForClasses";
    }

}
