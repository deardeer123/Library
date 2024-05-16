package com.green.Library.library.libraryhome.service;

import com.green.Library.library.borrowReturn.vo.BookBNRVO;
import com.green.Library.library.borrowReturn.vo.BookReservationVO;
import com.green.Library.library.libraryhome.vo.CalendarVO;
import com.green.Library.library.libraryhome.vo.MemoSearchVO;
import com.green.Library.library.libraryhome.vo.MemoVO;
import com.green.Library.libraryMember.vo.LibraryMemberVO;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.participationForum.vo.AskAndAnswerBoardVO;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface LibraryHomeService {

    // 관리자 로그인
    MemberVO login(MemberVO memberVO);


    List<CalendarVO> selectCalendarList();
    void insertCalendar(CalendarVO calendarVO);
    void deleteCalendar(CalendarVO calendarVO);
//    메모 작성
    void insertMemo(MemoVO memoVO);
//    메모 갯수
    int selectMemoCount(MemoSearchVO memoSearchVO);

//    메모리스트 조회(3개만)
    List<MemoVO> selectMemoList3();
//    메모리스트 조회
    List<MemoVO> selectMemoList(MemoSearchVO searchVO);
    //메모 하나만
    MemoVO selectMemoInfo(int id);
//    메모 삭제
    void deleteMemo(int id);
    //묻고 답하기 답변 안한 글 갯수
    int askBoardCount();
    //묻고 답하기 답변안한 글 5개
    List<BoardVO> notAskBoard();

    // 예약 최신 5개
    List<BookReservationVO> selectMainR();

    //대출 count
    int selectNowB();

    //반납 count
    int selectNowR();

    //미납 count
    int selectNowO(BookBNRVO bookBNRVO);

}
