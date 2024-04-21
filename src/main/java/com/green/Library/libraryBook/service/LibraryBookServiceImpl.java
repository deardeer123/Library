package com.green.Library.libraryBook.service;

import com.green.Library.libraryBook.vo.BookRecommendationVO;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("libraryBookService")
public class LibraryBookServiceImpl implements LibraryBookService{
    @Autowired
    SqlSessionTemplate sqlSession;

    @Override
    public String searchMaxCode() {
        return sqlSession.selectOne("libraryBookMapper.searchMaxCode");
    }

    @Override
    public List<LibraryBookCategoryVO> selectCateList() {
        return sqlSession.selectList("libraryBookMapper.selectCateList");
    }

    @Override
    public LibraryBookCategoryVO selectCateOne(int bookCateCode) {
        return sqlSession.selectOne("libraryBookMapper.selectCateOne",bookCateCode);
    }

    //책 추천하기
    @Override
    public void insertBookRecommendation(List<BookRecommendationVO> bookRecommendationList) {
        sqlSession.insert("libraryBookMapper.insertBookRecommendation", bookRecommendationList);
    }

    //추천책 보기
    @Override
    public Optional<List<BookRecommendationVO>> bookRecommendationList(String userType) {

        return Optional.ofNullable(sqlSession.selectList("libraryBookMapper.bookRecommendationList", userType));
    }

    //추천 취소하기
    @Override
    public void deleteBookRecommendation(List<BookRecommendationVO> bookRecommendationList) {
        sqlSession.delete("libraryBookMapper.bookRecommendationList",bookRecommendationList);

    }


}
