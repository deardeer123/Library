package com.green.Library.library.borrowReturn.service;

import com.green.Library.library.borrowReturn.vo.BookBorrowVO;
import com.green.Library.library.user.vo.UserVO;
import com.green.Library.web.member.vo.MemberVO;

public interface BorrowReturnService {

    // 이용자의 대출정보 조회
    MemberVO selectBorrowInfo(MemberVO memberVO);

    // 대출 시 book_borrow의 exReturnDate 업데이트
    void updateBookBorrow(BookBorrowVO bookBorrowVO);

    // 이용자 반납 update 및 대출 가능 여부 update
}
