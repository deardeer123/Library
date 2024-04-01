package com.green.Library.library.libraryMenu.service;

import com.green.Library.library.libraryMenu.vo.LibraryHeaderMenuVO;

import java.util.List;
import java.util.Map;

public interface LibraryMenuService {
    //메뉴 불러오기
    List<LibraryHeaderMenuVO> selectLibraryMenuList();

    List<LibraryHeaderMenuVO> selectLibraryMenuList2(String menuType);
    //페이지 이름으로 메뉴 인덱스 구하기
    Map<String, Integer> selectIndexNum(String pageName);
}
