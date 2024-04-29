package com.green.Library.web.webHome.controller;

import com.green.Library.library.libraryhome.service.LibraryHomeService;
import com.green.Library.library.libraryhome.vo.CalendarVO;
import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.web.board.service.BoardServiceImpl;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.member.service.MemberServiceImpl;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.participationForum.service.ParticipationForumService;
import com.green.Library.web.webHome.service.WebHomeService;
import com.green.Library.web.webHome.service.WebHomeServiceImpl;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import java.util.*;

@Controller
@RequestMapping("/")
public class WebHomeController {

    @Resource(name ="webMenuService")
    private WebMenuService webMenuService;
    @Resource(name = "memberService")
    private MemberServiceImpl memberService;
    @Resource(name = "boardService")
    private BoardServiceImpl boardService;
    @Resource(name="ParticipationForumService")
    private ParticipationForumService participationForumService;
    @Resource(name="libraryHomeService")
    private LibraryHomeService libraryHomeService;
    @Resource(name="webHomeService")
    private WebHomeService webHomeService;

    @RequestMapping("/home")
    public String goHome(Model model ,
                         BookSearchVO bookSearchVO ,
                         BoardVO boardVO ,
                         HttpSession session,
                        @RequestParam(name="type", defaultValue = "recommend", required = false)String type){
        //메뉴 정보 보내기
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));

        //로그인 네비게이션 정보.
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));
        //공지사항 3개
        model.addAttribute("noticeList", participationForumService.selectNotice3());
        //행사 3개
        model.addAttribute("eventList",boardService.selectEvent3());

        //처음엔 추천도서 3개
        switch (type){
            case "recommend":
                model.addAttribute("bookList", webHomeService.recommendedBookList3());
                model.addAttribute("type","추천도서");
                break;
            case "new":
                model.addAttribute("bookList", webHomeService.newBookList6());
                model.addAttribute("type","새로들어온책");
                break;
            default:
                model.addAttribute("bookList", webHomeService.manyBorrowBookList6());
                model.addAttribute("type", "대출이 많은책");

        }


        //신청한 행사 알림


        //검색 기능 필요해요



        System.out.println("홈");
        return "content/homePage/home";
    }


    @GetMapping("/test")
    public String goTest(Model model){
        //메뉴 정보 보내기
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //로그인 네비게이션 정보.
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));

        return "content/homePage/test1";
    }

    @GetMapping("/test2")
    public String goTest2(Model model){
        //메뉴 정보 보내기
        model.addAttribute("menuList",webMenuService.selectWebMenuList("web"));
        //로그인 네비게이션 정보.
        model.addAttribute("memberMenuList",webMenuService.selectWebMenuList("member"));

        return "content/homePage/test2";
    }

    @ResponseBody
    @PostMapping("/calendarData2Fetch")
    public List<Map<String, String>> calendarList(){
        List<CalendarVO> calendarVOList = libraryHomeService.selectCalendarList();
        List<Map<String, String>> mapList = new ArrayList<>();

//        이동시킬 url이 없으면 없애줘야함 그래야지 클릭이 안됨
//        불러들린 캘린더이벤트 반복시킴g
        calendarVOList.forEach(s->{
//            url이 널값인가 체크 널값이면 빈 문자열로 변환
            String nullChk = Optional.ofNullable(s).map(s1->s1.getUrl()).orElse("");
            if(nullChk.equals("")){
                //새로운 map 생성한뒤 maplist에 넣어주기
                Map<String, String> map1 = new HashMap<>();
                map1.put("title",Optional.ofNullable(s).map(s2->s2.getTitle()).orElse(""));
                map1.put("start",Optional.ofNullable(s).map(s2->s2.getStart()).orElse(""));
                map1.put("color",Optional.ofNullable(s).map(s2->s2.getColor()).orElse(""));
                mapList.add(map1);
            }else{
                //새로운 map 생성한뒤 maplist에 넣어주기
                Map<String, String> map1 = new HashMap<>();
                map1.put("title",Optional.ofNullable(s).map(s2->s2.getTitle()).orElse(""));
                map1.put("start",Optional.ofNullable(s).map(s2->s2.getStart()).orElse(""));
                map1.put("color",Optional.ofNullable(s).map(s2->s2.getColor()).orElse(""));
                map1.put("url",Optional.ofNullable(s).map(s2->s2.getUrl()).orElse(""));
                mapList.add(map1);
            }
        });
        //확인
        //System.out.println(mapList);

        return mapList;
    }

    @GetMapping("/goNotice")
    public String goNotice(@RequestParam(name = "boardNum")int boardNum){

        return "redirect:/noticeDetail?boardNum="+boardNum;
    }
}
