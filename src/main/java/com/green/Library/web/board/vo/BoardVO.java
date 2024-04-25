package com.green.Library.web.board.vo;


import com.green.Library.web.member.vo.ApplyVO;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.participationForum.vo.AskAndAnswerBoardVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardVO {
    private int boardNum;
    private String boardTitle;
    private String content;
    private String boardDate;
    private int userCode;
    private int boardCnt;
    private int boardType;
    private MovieVO movieVO;
    private MemberVO memberVO;
    private PlusVO plusVO;
    private ApplyVO applyVO;
    private List<Integer> boardNums;
    private List<UploadVO> fileList;
    //묻고 답하기
    private AskAndAnswerBoardVO askAndAnswerBoardVO;
}

