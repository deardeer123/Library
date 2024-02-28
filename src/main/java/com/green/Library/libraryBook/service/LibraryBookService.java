package com.green.Library.libraryBook.service;

import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;

import java.util.List;

public interface LibraryBookService {
    String searchMaxCode();
    List<LibraryBookCategoryVO> selectCateList();

}
