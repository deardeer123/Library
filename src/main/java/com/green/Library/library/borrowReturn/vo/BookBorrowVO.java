package com.green.Library.library.borrowReturn.vo;

import com.green.Library.libraryBook.vo.LibraryBookVO;
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
    private String borrowDate;
    private String returnYN;
    private String exReturnDate;
    private int userCode;
    private String bookCode;
    private LibraryBookVO libraryBookVO;
}
