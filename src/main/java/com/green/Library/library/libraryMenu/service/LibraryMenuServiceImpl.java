package com.green.Library.library.libraryMenu.service;

import com.green.Library.library.libraryMenu.vo.LibraryHeaderMenuVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("menuService")
public class LibraryMenuServiceImpl implements LibraryMenuService {
    @Autowired
    private SqlSessionTemplate sqlSession;
    @Override
    public List<LibraryHeaderMenuVO> selectLibraryMenuList() {
        //메뉴 불러오기
        return sqlSession.selectList("libraryMenuMapper.selectLibraryMenuList");
    }

    @Override
    public List<LibraryHeaderMenuVO> selectLibraryMenuList2(String menuType) {
        return sqlSession.selectList("libraryMenuMapper.selectLibraryMenuList2", menuType);
    }

    @Override
    public Map<String, Integer> selectIndexNum(String pageName) {
        return sqlSession.selectOne("libraryMenuMapper.selectIndexNum",pageName);
    }

}
