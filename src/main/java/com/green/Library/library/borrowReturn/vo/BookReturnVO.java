package com.green.Library.library.borrowReturn.vo;

import com.green.Library.libraryBook.vo.LibraryBookInfoVO;
import com.green.Library.libraryBook.vo.LibraryBookVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookReturnVO {
    private int rtCode;
    private String returnDate;
    private int returnUserCode;
    private String bookCode;
    private LibraryBookVO libraryBookVO;
    private LibraryBookInfoVO libraryBookInfoVO;
}
