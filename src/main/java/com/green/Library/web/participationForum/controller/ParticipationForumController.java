package com.green.Library.web.participationForum.controller;

import com.green.Library.util.BoardUploadUtil;
import com.green.Library.util.ConstantVariable;
import com.green.Library.web.board.service.BoardServiceImpl;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.board.vo.UploadVO;
import com.green.Library.web.member.service.MemberService;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.participationForum.service.ParticipationForumServiceIMPL;
import com.green.Library.web.participationForum.vo.AskAndAnswerBoardVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.green.Library.web.webMenu.service.WebMenuService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping("/")
public class ParticipationForumController {
    @Resource(name ="webMenuService")
    WebMenuService webMenuService;
    @Resource(name = "ParticipationForumService")
    private ParticipationForumServiceIMPL participationForumService;
    @Resource(name = "boardService")
    private BoardServiceImpl boardService;
    @Resource(name= "memberService")
    private MemberService memberService;

    private final int selectedMenuIndex = 4;


    //    -------- 참여마당(forum)---------

    //공지사항조회
    @RequestMapping("/notice")
    public String goNotice(Model model, SearchVO searchVO, HttpSession session){
        //인터셉터에 notice라는 정보를 넘겨줌
        model.addAttribute("page","notice");
        //boardType을 searchVO로 보내줘야함
        searchVO.setBoardType((int)webMenuService.selectIndexNum("notice").get("SIDE_MENU_NUM"));

        //총 게시판 갯수
        int boardCnt = participationForumService.partiCountBoard(searchVO);

        //페이징
        searchVO.setTotalDataCnt(boardCnt);
        searchVO.setPageInfo();

        //계속 이상하게 나오길래 넣은 코드입니다.
        if(boardCnt == 0){
            searchVO.setEndPage(1);
        }


        //묻는 글 들고오기
        List<BoardVO> boardVOList = participationForumService.selectNotice(searchVO);
        model.addAttribute("boardVOList", boardVOList);

        //현재 접속하고 있는 사람의 권한 정보가 필요한데.. 널값 체크 적당히 하면서 던져주기
        int userCode = Optional.ofNullable((Integer)session.getAttribute("userCode")).orElse((Integer) 0);
        Optional<MemberVO> memberOptional = Optional.ofNullable(memberService.selectMemberInfoToUserCode(userCode));
        String isAdmin = memberOptional.map(s-> s.getIsAdmin()).orElse("N");

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("userCode", userCode);


        //그냥 공지사항 갯수
        int normalTotalBoardCnt = participationForumService.normalTotalBoardCnt(searchVO);
        model.addAttribute("normalTotalBoardCnt", normalTotalBoardCnt);

        return "content/homePage/forum/notice";
    }


    //공지사항 글쓰기 페이지이동
    @GetMapping("/noticeWrite")
    public String noticeWrite (Model model, HttpSession session){
        //인터셉터한테 넘겨줄 page 정보
        model.addAttribute("page","notice");

        //관리자의 정보도 같이 던져주는 걸로
        MemberVO memberVO = memberService.selectMemberInfoToUserCode(Optional.ofNullable((Integer)session.getAttribute("userCode")).get());
        model.addAttribute("memberVO", memberVO);

        return "content/homePage/forum/noticeWrite";
    }

    //공지사항 글쓰기
    @PostMapping("/noticeWrite")
    public String noticeWrite(BoardVO boardVO, HttpSession session, Model model,
                              @RequestParam(name = "file") MultipartFile file){
        //인터셉터한테 넘겨줄 page 정보
        model.addAttribute("page","notice");

        //게시판 번호 구하기
        int boardNum = boardService.isNullBoardNo();
        boardVO.setBoardNum(boardNum);

        //userCode구하기
        int userCode = Optional.ofNullable((Integer)session.getAttribute("userCode")).orElse(boardVO.getUserCode());
        MemberVO memberVO = memberService.selectMemberInfoToUserCode(userCode);
        boardVO.setUserCode(userCode); //알아서 들고 오겠지만 혹시 몰라서 작성
        boardVO.setMemberVO(memberVO); //알아서 들고 오겠지만 혹시 몰라서 작성

        //혹시 파일을 파일을 업로드 하지않는경우를 생각하여 옵셔널 사용
        UploadVO uploadVO = Optional.ofNullable(BoardUploadUtil.uploadFile(file))
                .orElse(new UploadVO().builder()
                        .isMain("N")
                        .AttachedFileName("")
                        .OriginFileName("")
                        .build());

        //uploadVO에도 boardNum 넣어주기
        uploadVO.setBoardNum(boardNum);

        //fileList 만들고 그안에 uploadVO를 넣은 다음 boardVO에 넣어주기
        List<UploadVO> fileList = new ArrayList<>();
        fileList.add(uploadVO);
        boardVO.setFileList(fileList);

        //쿼리 실행
        participationForumService.insertNotice(boardVO);
        
        return "redirect:/notice";
    }
    //공지사항 글 상세조회
    @GetMapping("/noticeDetail")
    public String noticeDetail(@RequestParam(name = "boardNum") int boardNum, Model model, HttpSession session){
        //인터셉터한테 넘겨줄 page 정보
        model.addAttribute("page","notice");

        //조회수 증가
        participationForumService.boardCntUp(boardNum);

        //상세보기 할 게시판 찾기
        BoardVO boardVO = participationForumService.noticeDetail(boardNum);
        //글쓴 유저정보 뺴오기 위한 userCode
        int userCode = boardVO.getUserCode();
        MemberVO memberVO = memberService.selectMemberInfoToUserCode(userCode);

        //접속한 사람의 MemberVO
//        MemberVO memberVO1 = memberService.selectMemberInfoToUserCode(Optional.ofNullable((Integer)session.getAttribute("userCode")).get());
        MemberVO memberVO1 =Optional.ofNullable(memberService.selectMemberInfoToUserCode(
                        Optional.ofNullable((Integer)session.getAttribute("userCode")).orElse(0)))
                        .orElse(new MemberVO().builder()
                                .isAdmin("N")
                                .build());


        //자료 던져주기
//        model.addAttribute("memberVO1", memberVO1);
        model.addAttribute("boardVO", boardVO);
        model.addAttribute("memberVO", memberVO1);

        return "content/homePage/forum/noticeDetail";
    }

    //공지사항 수정하러가기
    @GetMapping("/modifyNoticeBoard")
    public String modifyNoticeBoard(Model model, @RequestParam(name ="boardNum")int boardNum){

        //넘겨받은 boardNum으로 게시물정보, 게시물 작성자 정보를 얻음
        BoardVO boardVO = participationForumService.noticeDetail(boardNum);
        MemberVO memberVO = memberService.selectMemberInfoToUserCode(boardVO.getUserCode());

        model.addAttribute("boardVO", boardVO);
        model.addAttribute("memberVO", memberVO);

        return "content/homePage/forum/modifyNoticeBoard";
    }

    //공지사항 변경하기
    @PostMapping("modifyNoticeBoard")
    public String modifyNoticeBoard2(BoardVO boardVO,UploadVO uploadVO,
                                     @RequestParam(name="file")MultipartFile file){
        //업로드된 파일의 코드
        int attchedCode = uploadVO.getAttachedCode();

        int boardNum = boardVO.getBoardNum();

        //새로 파일이 등록이 되었는지 확인
        if(!file.getOriginalFilename().equals("")){
            UploadVO uploadVO1 = BoardUploadUtil.uploadFile(file);
            uploadVO1.setBoardNum(boardNum);
            uploadVO1.setAttachedCode(attchedCode);

            participationForumService.modifyAskUpload(uploadVO1);
        }
        participationForumService.modifyAskBoard(boardVO);

        return "redirect:/notice";
    }

    //공지사항 삭제하기
    @GetMapping("goDeleteNoticeBoard")
    public String goDeleteNoticeBoard(@RequestParam(name="boardNum") int boardNum, Model model){

//       게시물 삭제할떄 boardNum 리스트로 보내줘야함..
        List<Integer> boardNumList = new ArrayList<>();
        boardNumList.add(boardNum);

        //boardNum이 담긴 리스트를 쿼리문으로 던져주기
        participationForumService.deleteBoard(boardNumList);

        return "redirect:/notice";
    }


    //묻고답하기
    @GetMapping("/askAndAnswer")
    public String goAskAndAnswer(Model model, SearchVO searchVO, HttpSession session){
        //인터셉터한테 ansAndAnswer 보내주기
        model.addAttribute("page", "askAndAnswer");
        //boardType을 searchVO로 보내줘야함
        searchVO.setBoardType((int)webMenuService.selectIndexNum("askAndAnswer").get("SIDE_MENU_NUM"));

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
       //묻는 글 들고오기
        List<BoardVO> boardVOList = participationForumService.selectAskAndAnswerBoardList(searchVO);
        model.addAttribute("boardVOList", boardVOList);

        //현재 접속하고 있는 사람의 권한 정보가 필요한데.. 널값 체크 적당히 하면서 던져주기
        int userCode = Optional.ofNullable((Integer)session.getAttribute("userCode")).orElse((Integer) 0);
        Optional<MemberVO> memberOptional = Optional.ofNullable(memberService.selectMemberInfoToUserCode(userCode));
        String isAdmin = memberOptional.map(s-> s.getIsAdmin()).orElse("N");

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("userCode", userCode);

        //그냥 공지사항 갯수
        int normalTotalBoardCnt = participationForumService.normalTotalBoardCnt(searchVO);
        model.addAttribute("normalTotalBoardCnt", normalTotalBoardCnt);

        return "content/homePage/forum/askAndAnswer";
    }

//    시큐리티 작업해야함 로그인해야지 글작성 페이지 이동할 수 있도록
    @GetMapping("/askAndAnswerWrite")
    public String writeAskAndAnswer(Model model,HttpSession session, SearchVO searchVO){
        //묻고 답하기 글 작성 페이지 이동
        //인터셉터한테 ansAndAnswer 보내주기
        model.addAttribute("page", "askAndAnswer");
        //boardType을 searchVO로 보내줘야함
        searchVO.setBoardType((int)webMenuService.selectIndexNum("askAndAnswer").get("SIDE_MENU_NUM"));

        //userCode로 이름, 전화번호, 아이디, 코드 얻기
        //근데 만약에 로그인을 하지 않고 글쓰기를 할려는 경우를 막아야함

        MemberVO memberVO = memberService.selectMemberInfoToUserCode((Integer) session.getAttribute("userCode"));
        model.addAttribute("memberVO",memberVO);

        return "content/homePage/forum/askAndAnswerWrite";
    }

    @PostMapping("/askAndAnswerWrite2")
    public String askAndAnswerWrite2(BoardVO boardVO, UploadVO uploadVO, AskAndAnswerBoardVO askAndAnswerBoardVO ,
            MemberVO memberVO ,
            HttpSession session,
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

        //askAndAnswerBoardVO에 정렬를 위한 정보 넣기
        int originOrderNum = participationForumService.selectMaxOriginOrderNum();
        askAndAnswerBoardVO.setOriginOrderNum(originOrderNum);

        //질문글 이므로 ANSWER_ORDER_NUM은 0 넣기
        int answerOrderNum = 0;
        askAndAnswerBoardVO.setAnswerOrderNum(answerOrderNum);

        //이글을 질문한 유저코드 넣기
        askAndAnswerBoardVO.setChkAskUserCode((Integer) session.getAttribute("userCode"));

        //boardVO에 askAndAnswerBoardVO 넣기
        boardVO.setAskAndAnswerBoardVO(askAndAnswerBoardVO);

        //memberVO도 넣어줘야 하는데 일단 가짜 데이터 홍길동 넣음

        memberVO.setUserCode((Integer) session.getAttribute("userCode"));
        //이게 안들어가지네...
        boardVO.setUserCode((Integer) session.getAttribute("userCode"));
        memberVO.setUserId((String) session.getAttribute("userId"));
        boardVO.setMemberVO(memberVO);

        participationForumService.insertAskAndAnswerBoard(boardVO);

        return "redirect:/askAndAnswer";
    }
    @ResponseBody
    @PostMapping("/askBoardPasswordCheckFetch")
    public Map<String, Object> askBoardPasswordCheck(@RequestParam(name= "passwd")String passwd,
                                        @RequestParam(name="boardNum")int boardNum){
        //넘어온 passwd와 게시판 비밀번호 비교 만약에 맞다면 isRight에 Y을 담고 아니면 N을 담음
        String isRight ="";

        //가져온 boardNum으로 해당 게시물의 비밀번호를 얻어와야함
        Optional<BoardVO> optionalBoardVO = Optional.ofNullable(participationForumService.detailAskBoard(boardNum));
        String chkPasswd = optionalBoardVO.map(s->s.getAskAndAnswerBoardVO()).map(s->s.getAskAndAnswerBoardPassword()).get();

        //비교해서 맞으면 Y 아니믄 N
        isRight = passwd.equals(chkPasswd)?"Y":"N";

        //map 타입으로 자료 던져주기
        Map<String, Object> map = new HashMap<>();
        map.put("isRight", isRight);
        map.put("boardNum", boardNum);

        return map;
    }

    @GetMapping("/goDetailAskBoard")
    public String goDetailAskBoard(Model model, @RequestParam(name="boardNum")int boardNum, HttpSession session){
        //묻고 답하기 게시물 보기
        model.addAttribute("page", "askAndAnswer");

        //게시물 정보 던져주기
        BoardVO boardVO = participationForumService.detailAskBoard(boardNum);
        //System.out.println(boardVO);

        int userCode = Optional.ofNullable((Integer)session.getAttribute("userCode")).orElse(0);
        MemberVO memberVO = Optional.ofNullable(memberService.selectMemberInfoToUserCode(userCode)).orElse(
                new MemberVO().builder()
                        .userCode(0)
                        .userId("Guest")
                        .userName("Guset")
                        .userTel("000-0000-0000")
                        .isAdmin("N")
                        .build()
        );
        model.addAttribute("boardVO",boardVO);
//        시큐리티 너무 어렵다 접속한사람 권한 확인할려고 던져줌
        model.addAttribute("memberVO",memberVO);

        return "content/homePage/forum/detailAskBoard";
    }

    //답변해주기
    @GetMapping("goAnswer")
    public String goAnswer(Model model, @RequestParam(name="boardNum") int boardNum){
        model.addAttribute("page", "askAndAnswer");

        //게시물 정보 던져주기
        Optional<BoardVO> boardVO1 = Optional.of(participationForumService.detailAskBoard2(boardNum));
        //System.out.println(boardVO);
        model.addAttribute("boardVO",boardVO1.get());

        return "content/homePage/forum/answerBoardWrite";
    }

    //답변
    @PostMapping("goAskAndAnswerWrite")
    public String goAskAndAnswerWrite(Model model,
                                      BoardVO boardVO,
                                      MemberVO memberVO,
                                      AskAndAnswerBoardVO askAndAnswerBoardVO){
        //답변하고자 하는 게시판 번호
        int askBoardNum = boardVO.getBoardNum();
        //게시판 번호 구하기
        int boardNum = boardService.isNullBoardNo();
        //게시판 타입 넣기
        boardVO.setBoardType(30);
        boardVO.setBoardNum(boardNum);
        boardVO.setBoardTitle("RE : " + boardVO.getBoardTitle());

        //uploadVO 객체 생성 및 초기화
        UploadVO uploadVO = new UploadVO().builder()
                .AttachedFileName("")
                .OriginFileName("")
                .isMain("N")
                .boardNum(boardNum)
                .build();
        List<UploadVO> uploadVOList = new ArrayList<>();
        uploadVOList.add(uploadVO);

        //묻고답하기 vo 설정
        askAndAnswerBoardVO.setBoardNum(boardNum); //게시판 번호
        askAndAnswerBoardVO.setIsAnswerBoard("Y"); //답변 게시판

        //답변한 게시판 번호
        askAndAnswerBoardVO.setIfAnswerBoardNum(askAndAnswerBoardVO.getAskAndAnswerBoardNum());
        askAndAnswerBoardVO.setOriginOrderNum(participationForumService.searchOriginOrderNumForAnswer(askBoardNum));

        //답변한 순서
        int answerNum = participationForumService.chkAnswerOrderNum(askBoardNum);
        //질문글의 맨처음 답변한경우에는 1
        //답변이 달린글에 또 답변이 달린 경우에는 1씩 증가 시켜야함
        if(answerNum == 0){
            answerNum = 1;

        }else{
            answerNum += 1;
        }
        askAndAnswerBoardVO.setAnswerOrderNum(answerNum);

        //boardVO에 객체 넣기
        boardVO.setMemberVO(memberVO);
        boardVO.setFileList(uploadVOList);
        boardVO.setAskAndAnswerBoardVO(askAndAnswerBoardVO);

        //답변 등록
        participationForumService.insertAskAndAnswerBoard(boardVO);


        return "redirect:/askAndAnswer";
    }
    //삭제 하기
    @GetMapping("/askBoardDelete")
    public String askBoardDelete(@RequestParam(name="boardNum") int boardNum ,
                                 @RequestParam(name="isAnswerBoard") String isAnswerBoard){

//        일단 boardNum으로 게시물 검색
        //게시물 정보 던져주기
        BoardVO boardVO = participationForumService.detailAskBoard(boardNum);
        int originOrderNum = boardVO.getAskAndAnswerBoardVO().getOriginOrderNum();

        //boardNum을 담을 리스트
        List<Integer> boardNumList = new ArrayList<>();

        //만약에 inAnswerBoard가 'Y'인 경우는 답글만 삭제하는 경우임
        if(isAnswerBoard.equals("Y")){
            boardNumList.add(boardNum);

        }else{//만약에 'N' 인경우는 질문글을 삭제하는 경우

            //던져준 originOrderNum을 통해 같은 originOrderNum을을 가지는 boardNum 값을 구하는 쿼리문
            //내가 적어놓고도 참 어이없다.
            List<Map<String, Integer>> map = participationForumService.deleteAskBoard1(originOrderNum);
            map.forEach(s-> boardNumList.add(s.get("board_num")));
        }

        //boardNum이 담긴 리스트를 쿼리문으로 던져주기
        participationForumService.deleteBoard(boardNumList);

        return "redirect:/askAndAnswer";
    }

    //    게시물 수정 하러 하기!!!
    @GetMapping("/goModifyAskBoard")
    public String goModifyAskBoard(@RequestParam(name = "boardNum")int boardNum, Model model){

        model.addAttribute("page", "askAndAnswer");

        Optional<BoardVO> optionalBoardVO = Optional.ofNullable(participationForumService.detailAskBoard2(boardNum));
        model.addAttribute(optionalBoardVO.get());

        return "content/homePage/forum/modifyAskBoard";
    }

    @PostMapping("/goModifyAskBoard2")
    public String goModifyAskBoard2(BoardVO boardVO, UploadVO uploadVO,
                                    @RequestParam(name="file")MultipartFile file){
        //업로드된 파일의 코드
        int attchedCode = uploadVO.getAttachedCode();
        
        //만약에 첨부파일이 없는 경우는 예전에 있던 값을 그대로 유지할 필요가 있음
        if(!file.getOriginalFilename().equals("")){
            //첨부파일 넣기
            uploadVO = BoardUploadUtil.uploadFile(file);

            //첨부파일 게시판 번호 넣어주기
            uploadVO.setBoardNum(boardVO.getBoardNum());
            uploadVO.setAttachedCode(attchedCode);
            //첨부파일 변경하기
            participationForumService.modifyAskUpload(uploadVO);
        }
        
        //수정하기
        participationForumService.modifyAskBoard(boardVO);

        return "redirect:/askAndAnswer";
    }





    @GetMapping("/bookDonation")
    public String goBookDonation(Model model){
        model.addAttribute("page", "bookDonation");

        System.out.println("자료기증");
        return "content/homePage/forum/bookDonation";
    }
    @GetMapping("/lockerReservation")
    public String goLockerReservation(Model model){
        model.addAttribute("page", "lockerReservation");

        System.out.println("사물함예약");
        return "content/homePage/forum/lockerReservation";

    }

}
