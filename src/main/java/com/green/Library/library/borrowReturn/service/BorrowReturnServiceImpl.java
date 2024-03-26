package com.green.Library.library.borrowReturn.service;

import com.green.Library.library.borrowReturn.vo.BookBNRVO;
import com.green.Library.web.member.vo.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("borrowReturnService")
public class BorrowReturnServiceImpl implements BorrowReturnService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    ////////////////////// 대출 ////////////////////////

    // 대출 정보 이력 조회
    @Override
    public MemberVO selectBorrowInfo(MemberVO memberVO) {
        return sqlSession.selectOne("bnrMapper.selectBorrowInfo", memberVO);
    }

    // 대출 시 BOOK_BNR INSERT + BOOK_INFO UPDATE
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBorrow(BookBNRVO bookBNRVO) {
        sqlSession.insert("bnrMapper.insertBorrow", bookBNRVO);
        sqlSession.update("bnrMapper.updateBookInfo", bookBNRVO.getBookCode());
    }

    // 책 코드 유효성 검사
    @Override
    public boolean isCorrectBookCode(String bookCode) {

        return sqlSession.selectOne("bnrMapper.isCorrectBookCode", bookCode) == null ? false : true;
    }

    // 대출 진행 시 카드번호에서 유저번호를 조회(인풋 태그가 하나라서)
    @Override
    public int selectUserCode(int cardNum) {
        return sqlSession.selectOne("bnrMapper.selectUserCode", cardNum);
    }


    ////////////////////// 반납 ////////////////////////////

    // 반납 수행 시 책의 이용 가능 여부 조회
    @Override
    public boolean selectBookAvailable(String bookCode) {
        return sqlSession.selectOne("bnrMapper.selectBookAvailable", bookCode).equals("Y") ? true : false;
    }

    // 반납 시 BOOK_BNR UPDATE + BOOK_INFO UPDATE
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReturnInfo(BookBNRVO bookBNRVO) {
        sqlSession.update("bnrMapper.updateReturnInfo", bookBNRVO);
        sqlSession.update("bnrMapper.updateReturnBookInfo", bookBNRVO.getBookCode());
    }


}
