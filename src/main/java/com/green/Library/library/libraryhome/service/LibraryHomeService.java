package com.green.Library.library.libraryhome.service;

import com.green.Library.library.libraryhome.vo.CalendarVO;
import com.green.Library.libraryMember.vo.LibraryMemberVO;
import com.green.Library.web.member.vo.MemberVO;

import java.util.List;

public interface LibraryHomeService {

    // 관리자 로그인
    MemberVO login(MemberVO memberVO);
    List<CalendarVO> selectCalendarList();
    void insertCalendar(CalendarVO calendarVO);
    void deleteCalendar(CalendarVO calendarVO);
}
