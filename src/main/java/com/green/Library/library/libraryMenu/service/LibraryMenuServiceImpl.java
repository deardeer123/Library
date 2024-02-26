package com.green.Library.library.libraryMenu.service;

import com.green.Library.library.libraryMenu.vo.LibraryHeaderMenuVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("menuService")
public class LibraryMenuServiceImpl implements LibraryMenuService {
    @Autowired
    SqlSessionTemplate sqlSession;
    @Override
    public List<LibraryHeaderMenuVO> selectMenuList() {
        //메뉴 불러오기
        return sqlSession.selectList("libraryMenuMapper.selectMenuList");
    }
}
