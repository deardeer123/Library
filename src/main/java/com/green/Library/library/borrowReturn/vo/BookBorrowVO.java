package com.green.Library.library.borrowReturn.vo;

import com.green.Library.libraryMember.vo.LibraryMemberVO;
import com.green.Library.web.member.vo.MemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookBorrowVO {
    private int brCode;
    private String brDate;
    private int bookCnt;
    private String returnYN;
    private String returnDate;
    private int userCode;
    private String bookCode;
    private MemberVO memberVO;
}
