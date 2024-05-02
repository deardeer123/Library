package com.green.Library.web.webHome.service;

import com.green.Library.web.findBook.vo.FindBookVO;

import java.util.List;
import java.util.Map;

public interface WebHomeService {
    //홈에 보여질 책들
    List<FindBookVO> recommendedBookList3();
    List<FindBookVO> newBookList6();
    List<FindBookVO> manyBorrowBookList6();
    List<Map<String,Object>> libraryEventList3();
}
