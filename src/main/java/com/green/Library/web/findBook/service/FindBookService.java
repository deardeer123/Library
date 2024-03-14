package com.green.Library.web.findBook.service;

import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.web.findBook.vo.FindBookVO;

import java.util.List;

public interface FindBookService {
//    책 검색
    List<FindBookVO> findBookList(BookSearchVO bookSearchVO);

    //책 하나 검색
    FindBookVO findBookOne(String bookCode);
//    책 갯수
    int selectFindBookCnt(BookSearchVO bookSearchVO);
}
