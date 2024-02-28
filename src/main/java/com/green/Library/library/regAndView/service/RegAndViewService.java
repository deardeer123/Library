package com.green.Library.library.regAndView.service;

import com.green.Library.libraryBook.vo.LibraryBookVO;

import java.util.List;

public interface RegAndViewService {
    //책 리스트
    List<LibraryBookVO> selectBookList();

    //책하나 찾기
    LibraryBookVO selectOneBook(String bookCode);

    //책 정보 변경하기
    void updateBook(LibraryBookVO libraryBookVO);







    //초기화
    void bookInfoInit(List<Integer> initList);

}
