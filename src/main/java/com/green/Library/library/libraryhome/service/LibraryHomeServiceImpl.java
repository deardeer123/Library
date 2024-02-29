package com.green.Library.library.libraryhome.service;

import com.green.Library.libraryMember.vo.LibraryMemberVO;
import com.green.Library.web.member.vo.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("libraryHomeService")
public class LibraryHomeServiceImpl implements LibraryHomeService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    // 관리자 로그인
    @Override
    public MemberVO login(MemberVO memberVO) {
        return sqlSession.selectOne("memberMapper.login", memberVO);
    }




}
