package com.green.Library.web.culturalAndEducation.controller;

import com.green.Library.util.BoardUploadUtil;
import com.green.Library.util.ScheduleService;
import com.green.Library.web.board.service.BoardService;
import com.green.Library.web.board.service.BoardServiceImpl;
import com.green.Library.web.board.vo.*;
import com.green.Library.web.culturalAndEducation.service.CulturalAndEducationServiceImpl;
import com.green.Library.web.member.service.MemberService;
import com.green.Library.web.member.service.MemberServiceImpl;
import com.green.Library.web.member.vo.ApplyVO;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Source;
import java.util.ArrayList;
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
                                 SearchVO searchVO,
                                 BoardVO boardVO){
        //인터셉터에 notice라는 정보를 넘겨줌
        model.addAttribute("page","libraryEvent");
        //boardType을 searchVO로 보내줘야함
        searchVO.setBoardType(webMenuService.selectIndexNum("libraryEvent").get("SIDE_MENU_NUM"));

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
    public String goEventBoard(SearchVO searchVO, Model model){

        //인터셉터에 libraryEvent 정보를 넘겨줌
        model.addAttribute("page","libraryEvent");

        return "content/homePage/culturalAndEducation/libraryEvent/event_board";
    }


    // 문화 게시판 등록
    @PostMapping("/cultureInsertBoard")
    public String cultureInsertBoard(Model model,
                                     HttpSession session,
                                     BoardVO boardVO,
                                     @RequestParam(name = "mainFile") MultipartFile mainFile,
                                     @RequestParam(name = "subFile") MultipartFile[] subFile){
        model.addAttribute("page","libraryEvent");


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
        model.addAttribute("page","libraryEvent");

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
        model.addAttribute("page","libraryEvent");

        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        BoardVO board = boardService.selectBoardDetail(boardVO.getBoardNum());
        System.out.println(board);
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
                                       ApplyVO applyVO,
                                       SearchVO searchVO,
                                       HttpSession session){
        //인터셉터에 libraryEvent 정보를 넘겨줌
        model.addAttribute("page","eventParticipation");


        int totalCulBoardCnt = boardService.isNullBoardNo();

        searchVO.setTotalDataCnt(totalCulBoardCnt);
        searchVO.setPageInfo();
        if(totalCulBoardCnt == 0){
            searchVO.setEndPage(1);
        }
        searchVO.setBoardType(25);

        int userCode = Optional.ofNullable((Integer) session.getAttribute("userCode")).orElse(0);
        applyVO.setUserCode(userCode);
        model.addAttribute("applyUser", memberService.selectApplyUser(applyVO.getUserCode()) );
        model.addAttribute("apply",boardService.applyBoardList());

        List<BoardVO> boardList =  boardService.selectPlusList(searchVO);
        model.addAttribute("boardList",boardList);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@"+ memberService.selectApplyUser(applyVO.getUserCode()));
        System.out.println(boardList);
        System.out.println("행사 참가신청");
        return "content/homePage/culturalAndEducation/eventParticipation/eventParticipation";
    }

    // 참가신청 게시글 등록 페이지 이동
    @GetMapping("/goParticipation")
    public String goParticipation(Model model){
        model.addAttribute("page","eventParticipation");
        return "content/homePage/culturalAndEducation/eventParticipation/goParticipation";
    }

    // 참가신청 게시글 등록
    @PostMapping("/insertParticipation")
    public String insertParticipation(Model model,
                                      BoardVO boardVO,
                                      PlusVO plusVO,
                                      HttpSession session,
                                      @RequestParam(name = "files") MultipartFile[] files){

        model.addAttribute("page","eventParticipation");

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
        model.addAttribute("page","eventParticipation");

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
        model.addAttribute("page","eventParticipation");

        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        BoardVO board = boardService.selectEventBoardDetail(boardVO.getBoardNum());
        model.addAttribute("board",board);
        System.out.println(board);
        return "content/homePage/culturalAndEducation/eventParticipation/goEventUpdatePage";
    }

    //게시글 수정
    @PostMapping("/goEventUpdate")
    public String goEventUpdate(BoardVO boardVO,PlusVO plusVO,Model model){
        model.addAttribute("page","eventParticipation");


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
    public String goMovie(Model model,
                          SearchVO searchVO,
                          HttpSession session,
                          MovieVO movieVO){
        //인터셉터에 movie 정보를 넘겨줌
        model.addAttribute("page","movie");
        //전체 게시글 수
        int totalCulBoardCnt = boardService.isNullBoardNo();

        searchVO.setTotalDataCnt(totalCulBoardCnt);
        searchVO.setPageInfo();
        if(totalCulBoardCnt == 0){
            searchVO.setEndPage(1);
        }
        searchVO.setBoardType(26);

        System.out.println(session.getAttribute("userName"));
        System.out.println(boardService.selectMovieList(searchVO));
        model.addAttribute("boardList",boardService.selectMovieList(searchVO));
        System.out.println("영화 상영");
        return "content/homePage/culturalAndEducation/movie/movie";
    }

    @GetMapping("/goInsertMoviePage")
    public String goInsertMoviePage(Model model){
        model.addAttribute("page","movie");
        return "content/homePage/culturalAndEducation/movie/movieInsertPage";
    }

    @PostMapping("/insertMovie")
    public String insertMovie(BoardVO boardVO,
                              @RequestParam(name = "files") MultipartFile[] files,
                              MovieVO movieVO){

        //boardNo의 max값
        int maxBoardNum = boardService.isNullBoardNo();

        boardVO.setBoardTitle(movieVO.getMovieName());


        // 멀티 이미지 첨부 기능
        List<UploadVO> fileList = BoardUploadUtil.subImgUploadFile(files);
        for (UploadVO file : fileList){
            file.setBoardNum(maxBoardNum);
        }
        boardVO.setFileList(fileList);
        boardVO.setBoardNum(maxBoardNum);

        boardVO.setMovieVO(movieVO);

        System.out.println(movieVO);
        System.out.println(boardVO);

        boardService.insertMovie(boardVO);
        return "redirect:/movie";
    }

    @GetMapping("/goDetailMoviePage")
    public String goDetailMoviePage(Model model,@RequestParam(name = "boardNum") int boardNum){
        model.addAttribute("page","movie");
        System.out.println(boardService.selectMovieDetail(boardNum));
        model.addAttribute("board", boardService.selectMovieDetail(boardNum));
        return "content/homePage/culturalAndEducation/movie/movieDetailPage";
    }

    @GetMapping("/goUpdateMoviePage")
    public String goUpdateMoviePage(Model model,
                                    @RequestParam(name = "boardNum") int boardNum){
        model.addAttribute("page","movie");
        System.out.println(boardService.selectMovieDetail(boardNum));
        model.addAttribute("board", boardService.selectMovieDetail(boardNum));
        return "content/homePage/culturalAndEducation/movie/movieUpdatePage";
    }

    @PostMapping("/updateMovie")
    public String updateMovie(BoardVO boardVO,
                              MovieVO movieVO,
                              UploadVO uploadVO,
                              @RequestParam(name = "file") MultipartFile file){
        //boardNo의 max값
        int maxBoardNum = boardService.isNullBoardNo();

        boardVO.setBoardTitle(movieVO.getMovieName());

        if(!uploadVO.getOriginFileName().equals(file.getOriginalFilename())){
            // 멀티 이미지 첨부 기능
            uploadVO = BoardUploadUtil.uploadFile(file);
            uploadVO.setIsMain("N");
            uploadVO.setBoardNum(boardVO.getBoardNum());
            boardService.updateFile(uploadVO);
        }
        boardVO.setBoardNum(boardVO.getBoardNum());

        boardVO.setMovieVO(movieVO);

        boardService.updateMovie(boardVO);
        return "redirect:/movie";
    }

    @GetMapping("/deleteMovie")
    public String deleteMovie(@RequestParam(name = "boardNum") int boardNum){
        boardService.movieDelete(boardNum);
        return "redirect:/movie";
    }



    ////////////////////////////////////////////////////////////////////////////////
    //    ----------------- 평생교육 강좌안내 -------------------
    @RequestMapping("/courseGuide")
    public String goCourseGuide(Model model,
                                SearchVO searchVO,
                                BoardVO boardVO){
        //인터셉터에 courseGuide 정보를 넘겨줌
        model.addAttribute("page","courseGuide");

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

    @GetMapping("/guideDetailBoard")
    public String guideDetailBoard(Model model, BoardVO boardVO, @RequestParam(name = "boardNum") int boardNum){
        model.addAttribute("page","courseGuide");

        boardVO.setBoardNum(boardNum);

        BoardVO board = boardService.selectBoardDetail(boardVO.getBoardNum());
        boardService.boardCntUp(boardVO.getBoardNum());
        model.addAttribute("board", board);
        System.out.println(board);
        return "content/homePage/culturalAndEducation/courseGuide/guideDetailBoard";
    }





    //가이드 게시판 등록 페이지 이동
    @GetMapping("/goGuideInsertPage")
    public String goGuideInsertPage(Model model){
        model.addAttribute("page","courseGuide");

        return "content/homePage/culturalAndEducation/courseGuide/guideInsertPage";
    }

    //가이드 게시판 등록
    @PostMapping("/guideInsertBoard")
    public String guideInsertBoard(Model model,
                                   HttpSession session,
                                   BoardVO boardVO,
                                   @RequestParam(name = "subFile") MultipartFile[] subFile){
        model.addAttribute("page","courseGuide");


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
    public String goApplicationForClasses(Model model, SearchVO searchVO, HttpSession session, ApplyVO applyVO){
        //인터셉터에 applicationForClasses 정보를 넘겨줌
        model.addAttribute("page","applicationForClasses");


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
        model.addAttribute("page","applicationForClasses");
        return "content/homePage/culturalAndEducation/applicationForClasses/goAppInsertPage";
    }

    //수강신청 게시글 등록 페이지
    @PostMapping("/appInsert")
    public String appInsert(BoardVO boardVO,
                            PlusVO plusVO,
                            HttpSession session,
                            @RequestParam(name = "files") MultipartFile[] files,
                            Model model){
        model.addAttribute("page","applicationForClasses");


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
        model.addAttribute("page","applicationForClasses");

        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        System.out.println("@@@@@@@@@@@@@@@@@" + boardService.applyBoardList());
        model.addAttribute("boardList", boardService.applyBoardList());
        return "content/homePage/culturalAndEducation/applicationForClasses/goApplyList";
    }

    //회원 신청 기록 페이지
    @GetMapping("/goApplyUserListPage")
    public String goApplyUserListPage(Model model,BoardVO boardVO,HttpSession session){
        model.addAttribute("page","applicationForClasses");

        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        model.addAttribute("userBoardList",memberService.applyUserBoardList(boardVO));
        return "content/homePage/culturalAndEducation/applicationForClasses/goApplyUserListPage";
    }

    //신청 취소
    @RequestMapping("/deleteApply")
    public String deleteApply(BoardVO boardVO){
        memberService.deleteApply(boardVO.getBoardNum());
        return "redirect:/goApplyUserListPage";
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

    //app 페이지 이동
    @GetMapping("/goAppDetailPage")
    public String goAppDetailPage(Model model, BoardVO boardVO,
                                        @RequestParam(name = "boardNum") int boardNum,
                                        ApplyVO applyVO,
                                        HttpSession session){
        model.addAttribute("page","applicationForClasses");

//        int userCode = Optional.ofNullable((Integer) session.getAttribute("userCode")).orElse(0);
//        applyVO.setUserCode(userCode);

        boardVO.setBoardNum(boardNum);
        applyVO.setUserCode((Integer) session.getAttribute("userCode"));
        model.addAttribute("check", boardService.applyCheck(applyVO));
        BoardVO board = boardService.selectEventBoardDetail(boardVO.getBoardNum());
        model.addAttribute("board", board);
        System.out.println(board);
        return "content/homePage/culturalAndEducation/applicationForClasses/goAppDetailBoard";
    }


    //app 게시물 수정 페이지 이동
    @GetMapping("/goAppDetailBoard")
    public String goAppDetailBoard(Model model, BoardVO boardVO, HttpSession session){
        model.addAttribute("page","applicationForClasses");

        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        BoardVO board = boardService.selectEventBoardDetail(boardVO.getBoardNum());
        model.addAttribute("board",board);
        System.out.println(board);
        return "redirect:/applicationForClasses";
    }







}
