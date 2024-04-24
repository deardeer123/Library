package com.green.Library.libraryBook.service;

import com.green.Library.libraryBook.vo.BookRecommendationVO;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import com.green.Library.web.findBook.vo.FindBookVO;
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
    //책 추천 변경

    //추천 및 변경 전 이미 등록되어 있는지 확인
    //등록이 되어 있지 않으면 insert , 등록이 되어 있으면 update
    @Override
    public void insertBookRecommendation(BookRecommendationVO bookRecommendationVO) {
        boolean chk = sqlSession.selectOne("libraryBookMapper.chkBookRecommendation",bookRecommendationVO);

        if(chk){
            sqlSession.update("libraryBookMapper.updateBookRecommendation",bookRecommendationVO);
        }else{
            sqlSession.insert("libraryBookMapper.insertBookRecommendation", bookRecommendationVO);
        }
    }

    //추천책 보기
    @Override
    public Optional<List<FindBookVO>> selectBookRecommendation(String userType) {

        return Optional.ofNullable(sqlSession.selectList("libraryBookMapper.selectBookRecommendation", userType));
    }

    //bookCode로 추천 타입 찾기
    @Override
    public Optional<String> selectBookRecommendationUserType(String bookCode) {
        return Optional.ofNullable(sqlSession.selectOne("libraryBookMapper.selectBookRecommendationUserType",bookCode));
    }

    //추천 취소하기
    @Override
    public void deleteBookRecommendation(List<BookRecommendationVO> bookRecommendationList) {
        sqlSession.delete("libraryBookMapper.bookRecommendationList",bookRecommendationList);

    }


}
