package com.green.Library.library.libraryhome.service;

import com.green.Library.libraryMember.vo.LibraryMemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("libraryHomeService")
public class LibraryHomeServiceImpl implements LibraryHomeService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    //admin 로그인
    @Override
    public LibraryMemberVO login(LibraryMemberVO libraryMemberVO) {
        return sqlSession.selectOne("libraryMemberMapper.login", libraryMemberVO);
    }




}
