package com.green.Library.web.culturalAndEducation.controller;

import com.green.Library.util.BoardUploadUtil;
import com.green.Library.util.UploadUtil;
import com.green.Library.web.board.service.BoardService;
import com.green.Library.web.board.service.BoardServiceImpl;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.culturalAndEducation.service.CulturalAndEducationServiceImpl;
import com.green.Library.web.culturalAndEducation.vo.CulturalAndEducationVO;
import com.green.Library.web.img.vo.ImgVO;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.participationForum.vo.ParticipationForumVO;
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
    CulturalAndEducationServiceImpl culturalAndEducationService;


    //    -------- 문화행사/교육(culturalAndEducation)---------
    @RequestMapping("/libraryEvent")
    public String goLibraryEvent(Model model, CulturalAndEducationVO culturalAndEducationVO, HttpSession session, SearchVO searchVO){
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
        int totalBoardCnt= boardService.countBoard();
        searchVO.setTotalDataCnt(totalBoardCnt);

        searchVO.setPageInfo();

        List<BoardVO> boardList = boardService.selectBoardList(searchVO);
        System.out.println(boardList);

        model.addAttribute("boardList",boardList);

        System.out.println("도서관행사");

        return "content/homePage/culturalAndEducation/libraryEvent";
    }


    // 문화 게시판 등록
    @PostMapping("/cultureInsertBoard")
    public String cultureInsertBoard(Model model,
                                     BoardVO boardVO,
                                     MemberVO memberVO,
                                     HttpSession session,
                                     @RequestParam(name = "mainImg") MultipartFile mainImg,
                                     @RequestParam(name = "subImgs") MultipartFile[] subImgs){
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
        int maxBoardNo = boardService.isNullBoardNo();

        // 단일 이미지 첨부 기능
        ImgVO mainImgVO = BoardUploadUtil.uploadFile(mainImg);
        mainImgVO.setBoardNo(maxBoardNo);

        // 멀티 이미지 첨부 기능
        List<ImgVO> imgList = BoardUploadUtil.subImgUploadFile(subImgs);
        for (ImgVO img : imgList){
            img.setBoardNo(maxBoardNo);
        }
        imgList.add(mainImgVO);
        boardVO.setImgList(imgList);
        System.out.println(boardVO);

        boardVO.setBoardNo(maxBoardNo);
        System.out.println(boardVO);
        boardService.insertCulBoard(boardVO);
        memberVO.setUserCode(loginInfo.getUserCode());


        return "redirect:/libraryEvent";
    }





    //도서관 행사 게시글 상세페이지
    @GetMapping("/eventDetailBoard")
    public String eventDetailBoard(Model model){
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




        return "content/homePage/culturalAndEducation/eventDetailBoard";
    }





    @GetMapping("/eventParticipation")
    public String goEventParticipation(Model model){
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
    @GetMapping("/courseGuide")
    public String goCourseGuide(Model model){
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

        System.out.println("평생교육 강좌안내");
        return "content/homePage/culturalAndEducation/courseGuide";
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
