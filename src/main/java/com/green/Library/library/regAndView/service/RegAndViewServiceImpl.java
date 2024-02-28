package com.green.Library.library.regAndView.service;

import com.green.Library.libraryBook.vo.LibraryBookVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    //책 정보 변경
    //먼저 책 테이블을 변경하고
    // 그다음 책 상세보기 변경하기

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBook(LibraryBookVO libraryBookVO) {
        sqlSession.update("regAndViewMapper.updateBook",libraryBookVO);
        sqlSession.update("regAndViewMapper.updateBookInfo",libraryBookVO);

    }

//    책 상세보기 초기화
    @Override
    public void bookInfoInit(List<Integer> initList) {
        sqlSession.insert("regAndViewMapper.bookInfoInit", initList);
    }


}
