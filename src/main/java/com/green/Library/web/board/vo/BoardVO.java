package com.green.Library.web.board.vo;

import com.green.Library.web.img.vo.ImgVO;
import com.green.Library.web.member.vo.MemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardVO {
    private int boardNo;
    private String boardTitle;
    private String content;
    private String boardDate;
    private int userCode;
    private int boardCnt;
    private int boardType;
    private MemberVO memberVO;
    private List<ImgVO> imgList;
}
