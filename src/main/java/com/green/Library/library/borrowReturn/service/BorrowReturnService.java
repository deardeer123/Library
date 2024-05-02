package com.green.Library.library.borrowReturn.service;

import com.green.Library.library.borrowReturn.vo.BookBNRVO;
import com.green.Library.library.borrowReturn.vo.BookReservationVO;
import com.green.Library.library.user.vo.SearchUserVO;
import com.green.Library.web.member.vo.MemberVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BorrowReturnService {

    ////////////////////이용자 정보 조회/////////////////////

    // 이용자의 대출정보 조회
    MemberVO selectBorrowInfo(MemberVO memberVO);

    //////////////////////대출 기능////////////////////////

    // 대출 시 book_borrow의 exReturnDate 업데이트
    void insertBorrow(BookBNRVO bookBNRVO);

    // input 태그에 입력한 책코드가 유효한 데이터인지 검사
    boolean isCorrectBookCode(String bookCode);

    // 조회 할 이용자 카드 번호 조회
    int selectUserCode(int cardNum);

    ////////////////////반납 기능///////////////////////

    // 책 이용 상태 조회
    boolean selectBookAvailable(String bookCode);

    // 이용자 반납 insert 및 대출 가능 여부 update
    void updateReturnInfo(BookBNRVO bookBNRVO);

    ///////////////////예약 기능////////////////////////

    // 예약 조회
    List<BookReservationVO> selectReservationList(SearchUserVO searchUserVO);

    // 개인 예약내역 확인
    List<BookReservationVO> selectOneReserveList(int userCode);

    // 반납 된 책의 예약 체크
    List<BookReservationVO> selectChkReservation(String bookCode);

    // 반납 된 책의 예약이 있을 경우 예약 날짜 update
    void updateHasReservation(BookReservationVO bookReservationVO);

    // 이용자가 해당 도서를 예약 한 게 맞는지 확인
    List<BookReservationVO> selectGetReservation(BookReservationVO bookReservationVO);

    // 예약 한 이용자가 기한 내에 책을 대출하러 왔을 경우 + 대출 시 BOOK_BNR INSERT + BOOK_INFO UPDATE
    void updateGetBorrow(BookBNRVO bookBNRVO);
}
