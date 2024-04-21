package com.green.Library.library.user.service;

import com.green.Library.library.user.vo.SearchUserVO;
import com.green.Library.library.user.vo.UserVO;
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
    public void updateCardNum(MemberVO memberVO) {
        sqlSession.update("userMenuMapper.updateCardNum", memberVO);
    }

    // 유저 상세정보 모달
    @Override
    public MemberVO showUserDetail(int userCode) {
        return sqlSession.selectOne("userMenuMapper.userSearch", userCode);
    }

    // 모달창 유저 상세정보 업데이터
    @Override
    public int userDetailUpdate(MemberVO memberVO) {
        return sqlSession.update("userMenuMapper.userDetailUpdate", memberVO);
    }


}
