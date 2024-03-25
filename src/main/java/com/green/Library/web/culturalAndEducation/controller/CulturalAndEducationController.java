package com.green.Library.web.culturalAndEducation.controller;

import com.green.Library.util.BoardUploadUtil;
import com.green.Library.web.board.service.BoardService;
import com.green.Library.web.board.service.BoardServiceImpl;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.PlusVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.board.vo.UploadVO;
import com.green.Library.web.culturalAndEducation.service.CulturalAndEducationServiceImpl;
import com.green.Library.web.member.service.MemberServiceImpl;
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
    private WebMenuService webMenuService;
    @Resource(name = "boardService")
    private BoardServiceImpl boardService;


    //    -------- 문화행사/교육(culturalAndEducation)---------
    @RequestMapping("/libraryEvent")
    public String goLibraryEvent(Model model,
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
        int totalCulBoardCnt = boardService.isNullBoardNo();
        searchVO.setTotalDataCnt(totalCulBoardCnt);
        searchVO.setPageInfo();
        searchVO.setBoardType(24);

//        boardVO.setMemberVO((MemberVO) session.getAttribute("userName"));
        List<BoardVO> boardList = boardService.selectBoardList(searchVO);
        System.out.println(boardList);

        model.addAttribute("boardList",boardList);
        System.out.println(session.getAttribute("userCode"));
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
    public String goDetailParticipation(Model model, BoardVO boardVO, @RequestParam(name = "boardNum") int boardNum, PlusVO plusVO){
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

        BoardVO board = boardService.selectEventBoardDetail(boardVO.getBoardNum());
        model.addAttribute("board", board);
        System.out.println(board);
        return "content/homePage/culturalAndEducation/eventParticipation/goDetailParticipation";
    }

    
    @GetMapping("/goDetail")
    public String goDetail(Model model){

        return "";
    }


    //신청하기 페이지 이동
    @GetMapping("/goApplyPage")
    public String goApplyPage(){

        return "content/homePage/culturalAndEducation/eventParticipation/goApplyPage";
    }


    //이벤트 게시물 수정 페이지 이동
    @GetMapping("/goEventUpdatePage")
    public String goEventUpdatePage(Model model, BoardVO boardVO, HttpSession session){
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


        int totalBoardCnt= boardService.countBoard();
        searchVO.setTotalDataCnt(totalBoardCnt);
        boardVO.setBoardType(27);
        searchVO.setPageInfo();
        searchVO.setBoardType(boardVO.getBoardType());


        List<BoardVO> boardList =  boardService.selectPlusList(searchVO);
        System.out.println("---------------------------------------------"+searchVO);
        System.out.println(boardList);
        model.addAttribute("guideBoardList",boardList);



        System.out.println("평생교육 강좌안내");
        return "content/homePage/culturalAndEducation/courseGuide/courseGuide";
    }

    //가이드 게시판 등록 페이지 이동
    @GetMapping("/goGuideInsertPage")
    public String goGuideInsertPage(){
        return "content/homePage/culturalAndEducation/courseGuide/guideInsertPage";
    }

    //가이드 게시판 등록
    @PostMapping("/guideInsertBoard")
    public String guideInsertBoard(Model model,
                                   BoardVO boardVO,
                                   HttpSession session,
                                   PlusVO plusVO,
                                   @RequestParam(name = "files") MultipartFile[] files) {
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
        return "redirect:/courseGuide";
    }

    //게시판 삭제
//    @PostMapping("/GuideDelete")
//    public String GuideDelete(@RequestParam(name = "boardNum")int boardNum){
//        boardService.eventBoardDelete(boardNum);
//        return "";
//    }
    //게시판 선택 삭제
    @PostMapping("/GuideDelete")
    public String GuideDeletes(BoardVO boardVO){
        boardService.selectDeletes(boardVO);
        return "redirect:/courseGuide";
    }
    //게시판 상세보기
    //게시판 수정






    ////////////////////////////////////////////////////

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
