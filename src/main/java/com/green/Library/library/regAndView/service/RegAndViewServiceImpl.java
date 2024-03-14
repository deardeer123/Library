package com.green.Library.library.regAndView.service;

import com.green.Library.library.borrowReturn.vo.BookBNRVO;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import com.green.Library.libraryBook.vo.LibraryBookVO;
import com.green.Library.web.member.vo.MemberVO;
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

    @Override
    public List<LibraryBookVO> searchBookList(BookSearchVO bookSearchVO) {
        return sqlSession.selectList("regAndViewMapper.searchBookList", bookSearchVO);
    }

    //    책 삭제하기
//    맨처음엔 bookinfo 지우고
//    그 다음엔 book 지우게함
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBookOne(String bookCode) {
        sqlSession.update("regAndViewMapper.DeleteBookOne", bookCode);
        sqlSession.update("regAndViewMapper.DeleteBookTwo", bookCode);
    }


    //책 갯수 검색
    @Override
    public int selectBookCnt(BookSearchVO bookSearchVO) {
        return sqlSession.selectOne("regAndViewMapper.selectBookCnt",bookSearchVO);
    }

    @Override
    public MemberVO bookDetailInfo(String bookCode) {
        return sqlSession.selectOne("regAndViewMapper.bookDetailInfo",bookCode);
    }
    //책 상세보기 할때 카테고리 이름
    @Override
    public LibraryBookCategoryVO selectCateNameOne(String bookCode) {
        return sqlSession.selectOne("regAndViewMapper.selectCateNameOne",bookCode);
    }

    //    책 상세보기 초기화
    @Override
    public void bookInfoInit(List<Integer> initList) {
        sqlSession.insert("regAndViewMapper.bookInfoInit", initList);
    }



}
