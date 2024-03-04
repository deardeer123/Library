package com.green.Library.library.borrowReturn.service;

import com.green.Library.library.borrowReturn.vo.BookBorrowVO;
import com.green.Library.library.borrowReturn.vo.MemberCardVO;

import java.util.List;

public interface BorrowReturnService {

    //이용자의 대출정보 조회
    MemberCardVO selectBorrowInfo(int userCode);
}
