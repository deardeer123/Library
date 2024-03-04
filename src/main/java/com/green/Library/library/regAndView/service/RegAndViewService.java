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

    //책 검색하기
    List<LibraryBookVO> searchBookList(BookSearchVO bookSearchVO);

    //책 삭제
    void deleteBookOne(String bookCode);

    //책 갯수 검색
    int selectBookCnt(BookSearchVO bookSearchVO);



    //초기화
    void bookInfoInit(List<Integer> initList);

}
