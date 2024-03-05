package com.green.Library.library.borrowReturn.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestDataVO {

    private int cardNum;
    private String bookCode;
}
