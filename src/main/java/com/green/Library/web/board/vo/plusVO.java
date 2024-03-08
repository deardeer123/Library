package com.green.Library.web.board.vo;

import lombok.Data;

@Data
public class plusVO {
    private int plusCode;
    private String teacher;
    private String target;
    private String openDate;
    private String closeDate;
    private String toDate;
    private String fromDate;
    private int personnel;
    private int maximumPerson;
    private String eventStatus;
    private int boardNum;
}
