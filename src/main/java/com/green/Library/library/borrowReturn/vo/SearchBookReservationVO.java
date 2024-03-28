package com.green.Library.library.borrowReturn.vo;

import lombok.Data;

@Data
public class SearchBookReservationVO {
    private String userName;
    private String bookTitle;
    private int cardNum;
    private String bookCode1;
    private String bookCode2;
    private String reserveDate1;
    private String reserveDate2;
    private String cancelReserveDate1;
    private String cancelReserveDate2;
}
