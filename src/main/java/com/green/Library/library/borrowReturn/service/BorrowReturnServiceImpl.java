package com.green.Library.library.borrowReturn.service;

import com.green.Library.library.user.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("borrowReturnService")
public class BorrowReturnServiceImpl implements BorrowReturnService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    // 대출 정보 이력 조회
    @Override
    public UserVO selectBorrowInfo(UserVO userVO) {
        return sqlSession.selectOne("borrowReturnMapper.selectBorrowInfo", userVO);
    }

    // 대출 정보 INSERT 및 책 정보 UPDATE
    //@Transactional(rollbackFor = Exception.class)

}
