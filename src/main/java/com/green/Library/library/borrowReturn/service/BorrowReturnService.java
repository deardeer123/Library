package com.green.Library.library.borrowReturn.service;

import com.green.Library.library.user.vo.UserVO;

public interface BorrowReturnService {

    // 이용자의 대출정보 조회
    UserVO selectBorrowInfo(UserVO userVO);

    // 이용자 대출 insert 및 대출 가능 여부 update


    // 이용자 반납 update 및 대출 가능 여부 update
}
