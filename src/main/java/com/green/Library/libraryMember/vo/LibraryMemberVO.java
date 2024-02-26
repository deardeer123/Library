package com.green.Library.libraryMember.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LibraryMemberVO {
    private int userCode;
    private String userId;
    private String userPw;
    private String userTel;
    private String userAddr;
    private String gender;
    private String email;
    private String isAdmin;
}
