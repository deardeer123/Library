package com.green.Library.library.borrowReturn.vo;

import com.green.Library.library.user.vo.UserVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MemberCardVO {
    private int memberNum;
    private String memberReg;
    private String memberPenalty;
    private int memberBookCnt;
    private String memberIntro;
    private int userCode;
    private int brCode;
    private List<BookBorrowVO> bookBorrowList;
    private List<BookReturnVO> bookReturnList;
}
