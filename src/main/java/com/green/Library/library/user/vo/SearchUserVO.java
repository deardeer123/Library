package com.green.Library.library.user.vo;

import com.green.Library.library.regAndView.service.PageVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.swing.*;

@Setter
@Getter
@ToString
public class SearchUserVO extends PageVO {
    private String userDetail;
    private int userCode;
    private String cardNum1;
    private String cardNum2;
    private String userName1;
    private String userName2;
    private String userTel;
    private String gender;
    private String orderStandard;
    private String searchBookTitle;
    private String bookCode1;
    private String bookCode2;
    private String reserveDate1;
    private String reserveDate2;
    private String reserveCancel1;
    private String reserveCancel2;
    private String borrowDate1;
    private String borrowDate2;
    private String returnDate1;
    private String returnDate2;

}
