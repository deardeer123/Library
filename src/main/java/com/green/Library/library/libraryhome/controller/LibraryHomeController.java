package com.green.Library.library.libraryhome.controller;


import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.library.libraryhome.service.LibraryHomeService;
import com.green.Library.library.libraryhome.service.LibraryHomeServiceImpl;
import com.green.Library.library.libraryhome.vo.CalendarVO;
import com.green.Library.libraryMember.vo.LibraryMemberVO;
import com.green.Library.web.member.vo.MemberVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.Encoder;
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
    @PostMapping("/calendarData")
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
        System.out.println(calendarVO);

        libraryHomeService.insertCalendar(calendarVO);

        return "redirect:/test2";
    }

    @PostMapping("/calendarDelete")
    public String calendarDelete(CalendarVO calendarVO){
        System.out.println(calendarVO);
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
        return "content/library/home";
    }









}

