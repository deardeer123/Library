package com.green.Library.library.user.vo;

import com.green.Library.library.regAndView.service.PageVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SearchUserVO extends PageVO {
    private int cardNum1;
    private int cardNum2;
    private String userName1;
    private String userName2;
    private String userTel;
    private String gender;
    private String orderStandard;
}
