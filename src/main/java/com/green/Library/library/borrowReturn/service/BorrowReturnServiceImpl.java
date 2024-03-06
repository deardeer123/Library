package com.green.Library.library.borrowReturn.service;

import com.green.Library.library.user.vo.UserVO;
import com.green.Library.web.member.vo.MemberVO;
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
    public MemberVO selectBorrowInfo(MemberVO memberVO) {
        return sqlSession.selectOne("borrowReturnMapper.selectBorrowInfo", memberVO);
    }

    // 대출 시 book_borrow의 exReturnDate 업데이트
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBookBorrow(String bookCode) {
        sqlSession.update("borrowReturnMapper.updateBookBorrow", bookCode);
        sqlSession.update("borrowReturnMapper.updateBookInfo", bookCode);
    }


}
