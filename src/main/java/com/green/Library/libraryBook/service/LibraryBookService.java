package com.green.Library.libraryBook.service;

import com.green.Library.libraryBook.vo.BookRecommendationVO;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import com.green.Library.web.findBook.vo.FindBookVO;

import java.util.List;
import java.util.Optional;

public interface LibraryBookService {
    //등록하기전 큰값 구하기
    String searchMaxCode();
    //카테고리
    List<LibraryBookCategoryVO> selectCateList();
    //카테고리 하나 찾기
    LibraryBookCategoryVO selectCateOne(int bookCateCode);

    //책 추천 등록
    void insertBookRecommendation(BookRecommendationVO bookRecommendationList);

    //책 추천 변경

    //추천 및 변경 전 이미 등록되어 있는지 확인
    //등록이 되어 있지 않으면 insert , 등록이 되어 있으면 update

    //추천된 책 찾기
    List<FindBookVO> selectBookRecommendation (String userType);

    //북코드로 추천타입 찾기
    Optional<String> selectBookRecommendationUserType (String bookCode);

    //책 추천 취소
    void deleteBookRecommendation(List<BookRecommendationVO> bookRecommendationList);

}
