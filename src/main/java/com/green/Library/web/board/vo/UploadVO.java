package com.green.Library.web.board.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadVO {
    private int AttachedCode;
    private String OriginFileName;
    private String AttachedFileName;
    private String isMain;
    private int boardNo;
}
