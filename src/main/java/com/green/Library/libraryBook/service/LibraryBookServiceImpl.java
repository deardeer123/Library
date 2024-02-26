package com.green.Library.libraryBook.service;

import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("libraryBookService")
public class LibraryBookServiceImpl implements LibraryBookService{
    @Autowired
    SqlSessionTemplate sqlSession;

    @Override
    public String searchMaxCode() {
        return sqlSession.selectOne("libraryBookMapper.searchMaxCode");
    }

    @Override
    public List<LibraryBookCategoryVO> selectCateList() {
        return sqlSession.selectList("libraryBookMapper.selectCateList");
    }

}
