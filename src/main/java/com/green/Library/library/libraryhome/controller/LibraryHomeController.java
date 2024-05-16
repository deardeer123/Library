package com.green.Library.library.libraryhome.controller;


import com.green.Library.library.borrowReturn.vo.BookBNRVO;
import com.green.Library.library.borrowReturn.vo.BookReservationVO;
import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.library.libraryhome.service.LibraryHomeService;
import com.green.Library.library.libraryhome.service.LibraryHomeServiceImpl;
import com.green.Library.library.libraryhome.vo.CalendarVO;
import com.green.Library.library.libraryhome.vo.MemoSearchVO;
import com.green.Library.library.libraryhome.vo.MemoVO;
import com.green.Library.libraryMember.vo.LibraryMemberVO;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.participationForum.vo.AskAndAnswerBoardVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import retrofit2.http.Path;

import java.beans.Encoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Controller
@RequestMapping("/bookAdmin")
public class LibraryHomeController {
    @Resource(name="menuService")
    LibraryMenuService libraryMenuService;

    @Resource(name = "libraryHomeService")
    private LibraryHomeService libraryHomeService;

    // 관리자 로그인 페이지 이동
    @GetMapping("/main")
    public String goLogin(){
        return "content/library/main";
    }


//    캘린더 정보 불러오기
    @ResponseBody
    @PostMapping("/calendarDataFetch")
    public List<Map<String, String>> calendarList(){
        List<CalendarVO> calendarVOList = libraryHomeService.selectCalendarList();
        List<Map<String, String>> mapList = new ArrayList<>();

//        이동시킬 url이 없으면 없애줘야함 그래야지 클릭이 안됨
//        불러들린 캘린더이벤트 반복시킴
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

    @PostMapping("/calendarAdd")
    public String calendarAdd(CalendarVO calendarVO, @RequestParam(name="start")String start){
        //날짜가 안넘어가서 그냥 start라는 변수로 넘겨줌
        calendarVO.setStart(start);
        String time = "12:00:00";
        //날짜 형식 맞춰주기:lㅏㅈ!
        calendarVO.setStart(calendarVO.getStart() + " "+time);
        //

        libraryHomeService.insertCalendar(calendarVO);

        return "redirect:/test2";
    }

    @PostMapping("/calendarDelete")
    public String calendarDelete(CalendarVO calendarVO){
        //
        libraryHomeService.deleteCalendar(calendarVO);
        return "redirect:/test2";
    }


    // 관리자 로그인
    @PostMapping("/login")
    public String login(MemberVO memberVO, HttpSession session){

        MemberVO loginInfo = libraryHomeService.login(memberVO);

        System.out.println(loginInfo);

        if(loginInfo != null){
            session.setAttribute("loginInfo", loginInfo);
        }

        return "redirect:/bookAdmin/home";
    }

    //홈
    @GetMapping("/home")
    public String goHome(Model model, HttpSession session){
        System.out.println("홈으로 이동");
        System.out.println(libraryMenuService.selectLibraryMenuList());

        MemberVO loginInfo = (MemberVO) session.getAttribute("loginInfo");
        model.addAttribute("loginInfo", loginInfo);

        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());

        //메모리스트 3개 보내주기
        model.addAttribute("memoList", libraryHomeService.selectMemoList3());

        //오늘 날짜
        LocalDate date = LocalDate.now(); //오늘 날짜 LocalDate 객체 생성
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = date.format(dateTimeFormatter); //LocalDate 객체를 String 객체로 바꿈
        model.addAttribute("today", today);

        //묻고 답하기 답변 안한 글 갯수
        model.addAttribute("askCount", libraryHomeService.askBoardCount());

        //묻고 답하기 답변 안한 글 5개
        List<BoardVO> askBoard = libraryHomeService.notAskBoard();
        askBoard.forEach(s-> System.out.println(s));
        model.addAttribute("askBoard", libraryHomeService.notAskBoard());

        // 예약 5개
        List<BookReservationVO> selectMainR = libraryHomeService.selectMainR();
        model.addAttribute("reservesInfo", selectMainR);

        // 대출
        model.addAttribute("borrowCnt", libraryHomeService.selectNowB());
        // 반납
        model.addAttribute("returnCnt", libraryHomeService.selectNowR());
        // 미납
        BookBNRVO bookBNRVO = new BookBNRVO();
        model.addAttribute("overdueCnt", libraryHomeService.selectNowO(bookBNRVO));

        return "content/library/home";
    }
    //메모 작성
    @PostMapping("memo/write")
    public String writeMemo(MemoVO memoVO){
        System.out.println(memoVO);
        System.out.println("메모작성");
        libraryHomeService.insertMemo(memoVO);
        return "redirect:/bookAdmin/home";
    }
    //검색
    @ResponseBody
    @PostMapping("memo")
    public Map<String,Object> selectMemoList(MemoSearchVO memoSearchVO){
        System.out.println("memoSearchVO = " + memoSearchVO);
        int memoCount = libraryHomeService.selectMemoCount(memoSearchVO);
        memoSearchVO.setTotalDataCnt(memoCount);
        memoSearchVO.setPageInfo();
        //계속 이상하게 나오길래 넣은 코드입니다.
        if(memoCount == 0){
            memoSearchVO.setEndPage(1);
        }

        List<MemoVO> memoList1 = libraryHomeService.selectMemoList(memoSearchVO);
        Map<String, Object> map = new HashMap<>();
        map.put("memoList",memoList1);
        map.put("searchVO", memoSearchVO);

        return map;
    }
    //하나 찾기
    @ResponseBody
    @GetMapping("memo/{id}")
    public MemoVO memoInfo(@PathVariable(name="id")int id){
        System.out.println(id);
        MemoVO memoVO = libraryHomeService.selectMemoInfo(id);
        return memoVO;
    }

    //삭제하기
    @ResponseBody
    @PostMapping("memo/{id}/delete")
    public Map<String, Object> deleteMemo(@PathVariable(name="id") int id, MemoSearchVO memoSearchVO){
        System.out.println("id = " + id);
        libraryHomeService.deleteMemo(id);

        int memoCount = libraryHomeService.selectMemoCount(memoSearchVO);
        memoSearchVO.setTotalDataCnt(memoCount);
        memoSearchVO.setPageInfo();
        //계속 이상하게 나오길래 넣은 코드입니다.
        if(memoCount == 0){
            memoSearchVO.setEndPage(1);
        }

        List<MemoVO> memoList1 = libraryHomeService.selectMemoList(memoSearchVO);
        Map<String, Object> map = new HashMap<>();
        map.put("memoList",memoList1);
        map.put("searchVO", memoSearchVO);

        return map;
    }

}

