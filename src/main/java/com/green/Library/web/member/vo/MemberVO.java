package com.green.Library.web.member.vo;

import com.green.Library.library.borrowReturn.vo.BookBorrowVO;
import com.green.Library.library.borrowReturn.vo.BookReturnVO;
import com.green.Library.libraryMember.vo.LibraryMemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
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
    private int cardNum;
    private String publishDate;
    private String userIntro;
    private String cardStatus;
    private List<BookBorrowVO> bookBorrowList;
    private List<BookReturnVO> bookReturnList;
}
