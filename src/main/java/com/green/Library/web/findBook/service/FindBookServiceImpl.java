package com.green.Library.web.findBook.service;

import com.green.Library.library.borrowReturn.vo.BookReservationVO;
import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.web.findBook.vo.FindBookVO;
import com.green.Library.web.findBook.vo.SearchDateVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("findBookService")
public class FindBookServiceImpl implements FindBookService{
    @Autowired
    SqlSessionTemplate sqlSession;

    @Override
    public List<FindBookVO> findBookList(BookSearchVO bookSearchVO) {
        return sqlSession.selectList("findBookMapper.findBookList",bookSearchVO);
    }

    @Override
    public FindBookVO findBookOne(String bookCode) {
        return sqlSession.selectOne("findBookMapper.findBookOne", bookCode);
    }

    @Override
    public int selectFindBookCnt(BookSearchVO bookSearchVO) {
        return sqlSession.selectOne("selectFindBookCnt",bookSearchVO);
    }

    //많이 빌린책
    @Override
    public List<FindBookVO> manyRentBook() {
        return sqlSession.selectList("findBookMapper.manyRentBook");
    }

    //추천책 목록 
    @Override
    public Optional<List<FindBookVO>> recommendedBookList(String userType) {
        return Optional.ofNullable(sqlSession.selectList("recommendedBookList", userType));
    }

    //새로 들러온 책
    @Override
    public List<FindBookVO> selectNewBookList(SearchDateVO searchDateVO) {
        return sqlSession.selectList("findBookMapper.selectNewBookList",searchDateVO);
    }
    //새로 들러온 책 검색된 책 갯수
    @Override
    public int selectNewBookCnt(SearchDateVO searchDateVO) {
        return sqlSession.selectOne("findBookMapper.selectNewBookCnt",searchDateVO);
    }

    ///////////////////////////////////////////////////////////////////
    @Override
    public void changeRegDate(int boardNum) {
        sqlSession.update("findBookMapper.changeRegDate",boardNum);
    }

    @Override
    public List<Integer> changeRegDate2() {
        return sqlSession.selectList("findBookMapper.changeRegDate2");
    }

    @Override
    public void bookReservationFetch(BookReservationVO bookReservationVO) {
        sqlSession.insert("findBookMapper.bookReservationFetch", bookReservationVO);
    }


}
