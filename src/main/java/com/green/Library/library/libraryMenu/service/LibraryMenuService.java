package com.green.Library.library.libraryMenu.service;

import com.green.Library.library.libraryMenu.vo.LibraryHeaderMenuVO;

import java.util.List;

public interface LibraryMenuService {
    //메뉴 불러오기
    List<LibraryHeaderMenuVO> selectLibraryMenuList();
}
