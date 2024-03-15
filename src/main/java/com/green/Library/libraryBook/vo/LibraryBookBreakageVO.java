package com.green.Library.libraryBook.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LibraryBookBreakageVO {
    private String bookCode;
    private String bookTitle;
    private String bookWriter;
    private String bookPub;
    private String bookYear;
    private LibraryBookBreakageInfoVO libraryBookBreakageInfoVO;
}
