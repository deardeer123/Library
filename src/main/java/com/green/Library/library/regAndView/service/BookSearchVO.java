package com.green.Library.library.regAndView.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class BookSearchVO extends PageVO{
    private String searchType;
    private String searchValue;
    private String searchDate;
    private String bookCateName;
    private String bookMidCateName;
    private String bookBorrowAvailable;

}
