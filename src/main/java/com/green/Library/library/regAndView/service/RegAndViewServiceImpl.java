package com.green.Library.library.regAndView.service;

import com.green.Library.libraryBook.vo.LibraryBookVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("regAndViewService")
public class RegAndViewServiceImpl implements RegAndViewService{
    @Autowired
    SqlSessionTemplate sqlSession;


    //왜 책 조회가 안될까?
    @Override
    public List<LibraryBookVO> selectBookList() {
        return sqlSession.selectList("regAndViewMapper.selectBookList");
    }

    //책하나 검색하기
    @Override
    public LibraryBookVO selectOneBook(String bookCode) {
        return sqlSession.selectOne("regAndViewMapper.selectOneBook",bookCode);
    }

    @Override
    public void bookInfoInit(List<Integer> initList) {
        sqlSession.insert("regAndViewMapper.bookInfoInit", initList);
    }
}
