package com.green.Library.web.webHome.service;

import com.green.Library.web.findBook.vo.FindBookVO;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("webHomeService")
public class WebHomeServiceImpl implements WebHomeService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public List<FindBookVO> recommendedBookList3() {
        return sqlSession.selectList("webHomeMapper.recommendedBookList3");
    }

    @Override
    public List<FindBookVO> newBookList6() {
        return sqlSession.selectList("webHomeMapper.newBookList6");
    }

    @Override
    public List<FindBookVO> manyBorrowBookList6() {
        return sqlSession.selectList("webHomeMapper.manyBorrowBookList6");
    }

    @Override
    public List<Map<String,Object>> libraryEventList3() {
        return sqlSession.selectList("webHomeMapper.libraryEventList3");
    }

}
