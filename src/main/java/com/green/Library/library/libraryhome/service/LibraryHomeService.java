package com.green.Library.library.libraryhome.service;

import com.green.Library.libraryMember.vo.LibraryMemberVO;
import com.green.Library.web.member.vo.MemberVO;

public interface LibraryHomeService {

    // 관리자 로그인
    MemberVO login(MemberVO memberVO);
}
