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

    @Override
    public List<MemberVO> selectUserInfoList(SearchUserVO searchUserVO) {
        return sqlSession.selectList("userMenuMapper.userSearch", searchUserVO);
    }

    @Override
    public void updateCardNum(MemberVO memberVO) {
        sqlSession.update("userMenuMapper.updateCardNum", memberVO);
    }
}
