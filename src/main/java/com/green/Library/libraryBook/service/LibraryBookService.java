package com.green.Library.libraryBook.service;

import com.green.Library.libraryBook.vo.BookRecommendationVO;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;

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
    void insertBookRecommendation(List<BookRecommendationVO> bookRecommendationList);

    //추천된 책 찾기
    Optional<List<BookRecommendationVO>> bookRecommendationList (String userType);

    //책 추천 취소
    void deleteBookRecommendation(List<BookRecommendationVO> bookRecommendationList);

}
