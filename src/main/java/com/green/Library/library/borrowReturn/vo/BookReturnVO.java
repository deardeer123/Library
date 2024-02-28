package com.green.Library.library.borrowReturn.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BookReturnVO {
    private int rtCode;
    private String rtDate;
    private int userCode;
    private String bookCode;
}
