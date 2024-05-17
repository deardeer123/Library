package com.green.Library.library.buy.service;

import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.libraryBook.vo.LibraryBookBreakageVO;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import com.green.Library.libraryBook.vo.LibraryBookVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("buyService")
public class BuyServiceImpl implements BuyService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    //책 등록하기
    //책 등록하기전에 책 먼저부터 등록한다음
    //그다음에 책 상세정보 등록하기
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void regBook(LibraryBookVO libraryBookVO) {
        sqlSession.insert("buyMapper.regBook", libraryBookVO);
        sqlSession.insert("buyMapper.regBookInfo", libraryBookVO);

    }

    @Override
    public int selectCntLibraryBookBreakage(BookSearchVO bookSearchVO) {
        return sqlSession.selectOne("buyMapper.selectCntLibraryBookBreakage",bookSearchVO);
    }

    @Override
    public List<LibraryBookBreakageVO> selectLibraryBookBreakageList(BookSearchVO bookSearchVO) {
        return sqlSession.selectList("buyMapper.selectLibraryBookBreakageList",bookSearchVO);
    }

    //파손된 책 정보 가져오기
    @Override
    public LibraryBookBreakageVO searchBookBreakageDetail(String bookCode) {
        return sqlSession.selectOne("buyMapper.searchBookBreakageDetail",bookCode);
    }
    //카테고리 정보 가져오기
    @Override
    public LibraryBookCategoryVO selectCateNameOne(String bookCode) {
        return sqlSession.selectOne("buyMapper.selectCateNameOne",bookCode);
    }

    
    //제외한 책 다시 되돌리기
    @Override
    public LibraryBookVO searchBookBreakageDetail2(String bookCode) {
        return sqlSession.selectOne("buyMapper.searchBookBreakageDetail2",bookCode);
    }

    //진짜 삭제
    @Override
    public void deleteBreakageBook(String bookCode) {
        sqlSession.delete("buyMapper.deleteBreakageBook", bookCode);
        sqlSession.delete("buyMapper.deleteBreakageBook2", bookCode);
    }

    @Override
    public void deleteBreakageBook2(String bookCode) {
        sqlSession.delete("buyMapper.deleteBreakageBook2", bookCode);
    }
}
