package com.green.Library.libraryBook.service;

import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;

import java.util.List;

public interface LibraryBookService {
    //등록하기전 큰값 구하기
    String searchMaxCode();
    //카테고리
    List<LibraryBookCategoryVO> selectCateList();
    //카테고리 하나 찾기
    LibraryBookCategoryVO selectCateOne(int bookCateCode);

}
