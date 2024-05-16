package com.green.Library.library.borrowReturn.vo;

import com.green.Library.libraryBook.vo.LibraryBookInfoVO;
import com.green.Library.libraryBook.vo.LibraryBookVO;
import com.green.Library.web.member.vo.MemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookBNRVO {
    private int borrowCode;
    private String borrowDate;
    private String returnYN;
    private String exReturnDate;
    private String returnDate;
    private int overdueDate;
    private int userCode;
    private String bookCode;
    private LibraryBookVO libraryBookVO;
}
