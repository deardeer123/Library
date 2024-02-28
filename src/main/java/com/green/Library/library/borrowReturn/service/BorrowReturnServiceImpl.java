package com.green.Library.library.borrowReturn.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowReturnServiceImpl implements BorrowReturnService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    // 대출정보 이력 조회

}
