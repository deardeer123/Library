package com.green.Library.library.borrowReturn.service;

import com.green.Library.library.borrowReturn.vo.BookBorrowVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("borrowReturnService")
public class BorrowReturnServiceImpl implements BorrowReturnService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    // 대출 정보 이력 조회
    @Override
    public List<BookBorrowVO> selectBorrowInfo(int userCode) {
        return sqlSession.selectList("borrowReturnMapper.selectBorrowInfo", userCode);
    }

    // 대출 정보 INSERT 및 책 정보 UPDATE


}
