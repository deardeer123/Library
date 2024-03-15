package com.green.Library.library.regAndView.service;

import com.green.Library.library.borrowReturn.vo.BookBNRVO;
import com.green.Library.libraryBook.vo.LibraryBookBreakageVO;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import com.green.Library.libraryBook.vo.LibraryBookVO;
import com.green.Library.web.member.vo.MemberVO;

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

    MemberVO bookDetailInfo(String bookCode);

    LibraryBookCategoryVO selectCateNameOne(String bookCode);

    //제외한 책 옮기기

    void insertBookBreakageOne(LibraryBookVO libraryBookVO);
    //LibraryBookBreakageVO insertBookBreakageInfoOne(LibraryBookVO libraryBookVO);





    //초기화
    void bookInfoInit(List<Integer> initList);

}
