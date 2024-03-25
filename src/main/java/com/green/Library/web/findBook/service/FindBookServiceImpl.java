package com.green.Library.web.findBook.service;

import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.web.findBook.vo.FindBookVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("findBookService")
public class FindBookServiceImpl implements FindBookService{
    @Autowired
    SqlSessionTemplate sqlSession;

    @Override
    public List<FindBookVO> findBookList(BookSearchVO bookSearchVO) {
        return sqlSession.selectList("findBookMapper.findBookList",bookSearchVO);
    }

    @Override
    public FindBookVO findBookOne(String bookCode) {
        return sqlSession.selectOne("findBookMapper.findBookOne", bookCode);
    }

    @Override
    public int selectFindBookCnt(BookSearchVO bookSearchVO) {
        return sqlSession.selectOne("selectFindBookCnt",bookSearchVO);
    }
}
