package com.green.Library.library.borrowReturn.service;

import com.green.Library.library.borrowReturn.vo.BookBNRVO;
import com.green.Library.library.borrowReturn.vo.BookReservationVO;
import com.green.Library.library.borrowReturn.vo.SearchBookReservationVO;
import com.green.Library.web.member.vo.MemberVO;

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
    List<BookReservationVO> selectReserve(SearchBookReservationVO searchBookReservationVO);

}
