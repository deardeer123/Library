package com.green.Library.web.findBook.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FindBookVO {
    private String bookCode;
    private String bookTitle;
    private String bookWriter;
    private String bookPub;
    private String bookYear;
    private String bookBorrowAvailable;
    private String bookIntro;
    private String bookInfoAttachedFileName;
    private String bookCateName;
    private String bookMidCateName;
    private String bookRegDate;
    private long id;
    private String userType;
}
