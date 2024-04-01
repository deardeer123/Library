package com.green.Library.library.borrowReturn.vo;

import com.green.Library.libraryBook.vo.LibraryBookInfoVO;
import com.green.Library.libraryBook.vo.LibraryBookVO;
import com.green.Library.web.member.vo.MemberVO;
import lombok.Data;

import java.util.List;

@Data
public class BookReservationVO {
    private int reserveCode;
    private int userCode;
    private String bookCode;
    private String reserveDate;
    private String reserveCancel;
    private String reserveStatus;
    private int reserveCnt;
    private MemberVO memberVO;
    private LibraryBookVO libraryBookVO;
}
