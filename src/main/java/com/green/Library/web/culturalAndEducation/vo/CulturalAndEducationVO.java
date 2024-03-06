package com.green.Library.web.culturalAndEducation.vo;

import com.green.Library.web.board.vo.UploadVO;
import com.green.Library.web.member.vo.MemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CulturalAndEducationVO {
    private int boardNo;
    private String boardTitle;
    private String content;
    private String boardDate;
    private int userCode;
    private int boardCnt;
    private int boardType;
    private MemberVO memberVO;
    private List<UploadVO> uploadList;
}
