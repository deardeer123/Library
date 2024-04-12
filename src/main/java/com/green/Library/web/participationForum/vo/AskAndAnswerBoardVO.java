package com.green.Library.web.participationForum.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class AskAndAnswerBoardVO {
    private int askAndAnswerBoardNum;
    private String askAndAnswerBoardPassword;
    private String askAndAnswerBoardPublicOrPrivate;
    private String isAnswerBoard;
    private int ifAnswerBoardNum;
    private int chkAskUserCode;
    private int originOrderNum;
    private int answerOrderNum;
    private int boardNum;
}
