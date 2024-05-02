package com.green.Library.library.user.service;

import com.green.Library.library.user.vo.SearchUserVO;
import com.green.Library.web.member.vo.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    // 이용자 정보 조회
    @Override
    public List<MemberVO> selectUserInfoList(SearchUserVO searchUserVO) {
        return sqlSession.selectList("userMenuMapper.userSearch", searchUserVO);
    }

    // 카드번호 부여
    @Override
    public int updateCardNum(MemberVO memberVO) {
        return sqlSession.update("userMenuMapper.updateCardNum", memberVO);
    }

    // 유저 상세정보 모달
    @Override
    public MemberVO showUserDetail(int userCode) {
        return sqlSession.selectOne("userMenuMapper.userSearch", userCode);
    }

    // 모달창 유저 상세정보 업데이터
    @Override
    public void userDetailUpdate(MemberVO memberVO) {
        sqlSession.update("userMenuMapper.userDetailUpdate", memberVO);
    }

    // 이용자 소개 업데이트
    @Override
    public void updateUserIntro(MemberVO memberVO) {
        sqlSession.update("userMenuMapper.updateUserIntro", memberVO);
    }

    @Override
    public int selectCardNum(int userCode) {
        return sqlSession.selectOne("userMenuMapper.selectCardNum", userCode);
    }

}
