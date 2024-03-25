package com.green.Library.libraryBook.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Setter
@Getter
public class LibraryBookCategoryVO {
    private int bookCateCode;
    private String bookCateName;
    private int bookCateIndex;
    private List<LibraryBookMidCategoryVO> libraryBookMidCategoryVOList;
}
