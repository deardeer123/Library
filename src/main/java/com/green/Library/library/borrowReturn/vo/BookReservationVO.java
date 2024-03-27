package com.green.Library.library.borrowReturn.vo;

import lombok.Data;

@Data
public class BookReservationVO {
    private int reservCode;
    private int userCode;
    private String bookCode;
    private String reservDate;
    private String reservCancel;
    private String reservStatus;
    private int reservCnt;
}
