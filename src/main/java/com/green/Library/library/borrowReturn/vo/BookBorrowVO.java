package com.green.Library.library.borrowReturn.vo;

import com.green.Library.libraryMember.vo.LibraryMemberVO;
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
    private LibraryMemberVO libraryMember;
}
