package com.green.Library.web.culturalAndEducation.vo;

import lombok.Data;

@Data
public class EventBoardVO {
    private int eventCode;
    private String eventTitle;
    private String teacher;
    private String target;
    private String eventOpen;
    private String eventClose;
    private String toDate;
    private String fromDate;
    private int personnel;
    private int maximumPerson;
    private String statusIndex;
}
