package com.green.Library.web.webMenu.service;

import com.green.Library.web.webMenu.vo.WebHeaderMenuVO;

import java.util.List;
import java.util.Map;

public interface WebMenuService {
    //메뉴 찾기
    List<WebHeaderMenuVO> selectWebMenuList(String menuType);

    //대충 페이지 이름 넘겨주면 해당하는 인덱스 번호 주는 메소드
    Map<String, Object> selectIndexNum(String page);
}
