package com.green.Library.library.borrowReturn.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookBorrowVO {
    private int brCode;
    private String brDate;
    private int rtCode;
    private int userCode;
    private String bookCode;

}
