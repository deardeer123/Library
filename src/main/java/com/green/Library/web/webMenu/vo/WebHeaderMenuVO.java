package com.green.Library.web.webMenu.vo;

import com.green.Library.library.libraryMenu.vo.LibrarySideMenuVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

public class WebHeaderMenuVO {
    private int menuNum;
    private String menuName;
    private String menuPage;
    private int menuIndex;
    private String menuType;
    private List<WebSideMenuVO> webSideMenuVOList;
}
