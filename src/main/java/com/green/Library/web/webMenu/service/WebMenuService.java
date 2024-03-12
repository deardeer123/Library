package com.green.Library.web.webMenu.service;

import com.green.Library.web.webMenu.vo.WebHeaderMenuVO;

import java.util.List;

public interface WebMenuService {
    List<WebHeaderMenuVO> selectWebMenuList(String menuType);
}
