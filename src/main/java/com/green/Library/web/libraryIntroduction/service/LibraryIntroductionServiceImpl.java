package com.green.Library.web.libraryIntroduction.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("libraryIntroductionService")
public class LibraryIntroductionServiceImpl implements LibraryIntroductionService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public List<Map<String, Object>> libraryState() {
        return sqlSession.selectList("libraryIntroductionMapper.libraryState");
    }

    @Override
    public Integer libraryState2() {
        return sqlSession.selectOne("libraryIntroductionMapper.libraryState2");
    }
}
