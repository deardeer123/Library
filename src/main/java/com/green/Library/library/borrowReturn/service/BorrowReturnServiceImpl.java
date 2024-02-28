package com.green.Library.library.borrowReturn.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowReturnServiceImpl implements BorrowReturnService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    // 이용자 조회, 대출, BOOK_INFO의 이용가능 여부 변경
    //@Transactional(rollbackFor = Exception.class)

}
