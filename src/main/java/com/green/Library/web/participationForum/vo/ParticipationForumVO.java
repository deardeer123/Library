package com.green.Library.web.participationForum.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParticipationForumVO {
    private int boardNo;
    private String boardTitle;
    private String content;
    private String boardDate;
    private int userCode;
    private int boardCnt;
    private int boardType;
}
