package com.green.Library.libraryBook.vo;

import com.green.Library.libraryMember.vo.LibraryMemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LibraryBookVO {
    private String bookCode;
    private String bookTitle;
    private String bookWriter;
    private String bookPub;
    private String bookYear;
    private LibraryBookInfoVO libraryBookInfoVO;
    private BookRecommendationVO bookRecommendationVO;
}
