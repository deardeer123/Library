package com.green.Library.web.member.vo;

import com.green.Library.library.borrowReturn.vo.BookBNRVO;
import com.green.Library.library.borrowReturn.vo.BookReservationVO;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
    private int userCode;
    private String userId;
    private String userPw;
    private String userName;
    private String userTel;
    private int postCode;
    private String userAddr;
    private String addrDetail;
    private String gender;
    private String email;
    private String isAdmin;
    private Integer cardNum;
    private String publishDate;
    private String userIntro;
    private String cardStatus;
    private String recentDate;
    private int borrowCnt;//
    private int overdueCnt;//
    private ApplyVO applyVO;
    private List<BookBNRVO> bookBorrowList;
}
