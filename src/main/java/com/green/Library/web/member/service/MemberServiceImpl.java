package com.green.Library.web.member.service;

import com.green.Library.web.member.vo.ApplyVO;
import com.green.Library.web.member.vo.MemberVO;
import org.codehaus.groovy.transform.trait.Traits;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberService")
public class MemberServiceImpl implements MemberService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public void insertMember(MemberVO memberVO) {
        sqlSession.insert("memberMapper.insertMember",memberVO);
    }

    @Override
    public MemberVO login(String memberId) {
        return sqlSession.selectOne("memberMapper.login",memberId);
    }

    @Override
    public void apply(ApplyVO applyVO) {
        sqlSession.insert("memberMapper.apply",applyVO);
    }

    @Override
    public List<ApplyVO> applyList() {
        return sqlSession.selectList("memberMapper.applyList");
    }


}
