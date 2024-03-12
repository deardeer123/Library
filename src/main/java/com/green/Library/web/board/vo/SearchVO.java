package com.green.Library.web.board.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchVO extends PageVO{
    private String searchValue;
    private String searchType;
    private int boardType;
    private String fromDate;
    private String toDate;
}
