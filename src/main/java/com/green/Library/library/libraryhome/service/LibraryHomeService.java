package com.green.Library.library.libraryhome.service;

import com.green.Library.libraryMember.vo.LibraryMemberVO;

public interface LibraryHomeService {

    // 관리자 로그인
    LibraryMemberVO login(LibraryMemberVO libraryMemberVO);
}
