package com.green.Library.library.buy.service;

import com.green.Library.libraryBook.vo.LibraryBookVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("buyService")
public class BuyServiceImpl implements BuyService{
    @Autowired
    SqlSessionTemplate sqlSession;

    //책 등록하기
    //책 등록하기전에 책 먼저부터 등록한다음
    //그다음에 책 상세정보 등록하기
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void regBook(LibraryBookVO libraryBookVO) {
        sqlSession.insert("buyMapper.regBook", libraryBookVO);
        sqlSession.insert("buyMapper.regBookInfo", libraryBookVO);

    }
}
