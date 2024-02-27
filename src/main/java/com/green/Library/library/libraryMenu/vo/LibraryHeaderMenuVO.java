package com.green.Library.library.libraryMenu.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LibraryHeaderMenuVO {
    private int menuNum;
    private String menuName;
    private String menuPage;
    private int menuIndex;
    private String menuType;
    private List<LibrarySideMenuVO> LibrarySideMenuVOList;

}
