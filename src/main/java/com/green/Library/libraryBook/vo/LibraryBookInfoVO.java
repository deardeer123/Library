package com.green.Library.libraryBook.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LibraryBookInfoVO {
    private int bookInfoNum;
    private String bookInfoAttachedFileName;
    private String bookInfoOriginFileName;
    private String bookBorrowAvailable;
    private String bookBorrowCnt;
    private String bookIntro;
    private int bookCateCode;
    private String bookCode;
}
