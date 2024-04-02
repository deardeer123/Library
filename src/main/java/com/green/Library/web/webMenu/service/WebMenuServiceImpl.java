package com.green.Library.web.webMenu.service;

import com.green.Library.web.webMenu.vo.WebHeaderMenuVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("webMenuService")
public class WebMenuServiceImpl implements WebMenuService{
    @Autowired
    SqlSessionTemplate sqlSession;
    //메뉴 찾기
    @Override
    public List<WebHeaderMenuVO> selectWebMenuList(String menuType) {
        return sqlSession.selectList("selectWebMenuList",menuType);
    }

    //대충 페이지 이름 넘겨주면 해당하는 인덱스 번호 주는 메소드
    @Override
    public Map<String, Integer> selectIndexNum(String page) {
        return sqlSession.selectOne("webMenuMapper.selectIndexNum",page);
    }
}
