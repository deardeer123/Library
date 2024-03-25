package com.green.Library.web.webMenu.service;

import com.green.Library.web.webMenu.vo.WebHeaderMenuVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("webMenuService")
public class WebMenuServiceImpl implements WebMenuService{
    @Autowired
    SqlSessionTemplate sqlSession;
    @Override
    public List<WebHeaderMenuVO> selectWebMenuList(String menuType) {
        return sqlSession.selectList("selectWebMenuList",menuType);
    }
}
