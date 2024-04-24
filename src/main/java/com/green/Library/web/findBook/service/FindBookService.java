package com.green.Library.web.findBook.service;

import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.web.findBook.vo.FindBookVO;
import com.green.Library.web.findBook.vo.SearchDateVO;

import java.util.List;
import java.util.Optional;

public interface FindBookService {
//    책 검색
    List<FindBookVO> findBookList(BookSearchVO bookSearchVO);

    //책 하나 검색
    FindBookVO findBookOne(String bookCode);
//    책 갯수
    int selectFindBookCnt(BookSearchVO bookSearchVO);
    //대출 많이한책 10권 찾기
    List<FindBookVO> manyRentBook ();

    //추천도서 책 불러오기
    Optional<List<FindBookVO>> recommendedBookList(String userType);

    //새로들어온책 검색
    List<FindBookVO> selectNewBookList(SearchDateVO searchDateVO);

    int selectNewBookCnt(SearchDateVO searchDateVO);

    //프로시저 쓸 줄 몰라서 사용한 거 입니다. 쓰지마세요
    void changeRegDate(int boardNum);

    //프로시저 쓸 줄 몰라서 사용한 거 입니다. 쓰지마세요
    List<Integer> changeRegDate2();
}
