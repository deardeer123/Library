package com.green.Library.web.board.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadVO {
    private int uploadCode;
    private String uploadOriginFileName;
    private String uploadAttachedFileName;
    private int boardNo;
}
