package com.green.Library.library.buy.service;

import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.libraryBook.vo.LibraryBookBreakageVO;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import com.green.Library.libraryBook.vo.LibraryBookVO;

import java.util.List;

public interface BuyService {
    //책 등록
    void regBook(LibraryBookVO libraryBookVO);


    //제외한 책 갯수 새기
    int selectCntLibraryBookBreakage(BookSearchVO bookSearchVO);

    //제외한 책 확인하기
    List<LibraryBookBreakageVO> selectLibraryBookBreakageList(BookSearchVO bookSearchVO);

    //제외한 책 하나 검색하기
    LibraryBookBreakageVO searchBookBreakageDetail(String bookCode);
    //제외한 책 카테고리 정보 가져오기
    LibraryBookCategoryVO selectCateNameOne(String bookCode);

    //제외한책 다시 원래 책으로 되돌리기 1
    LibraryBookVO searchBookBreakageDetail2(String bookCode);
    //제외한 책 삭제하기
    void deleteBreakageBook(String bookCode);
    //제외한 책 다시 원래 책으로 되돌리기
    void deleteBreakageBook2(String bookCode);

    //진짜

}
