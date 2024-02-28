package com.green.Library.web.culturalAndEducation.vo;

import com.green.Library.web.member.vo.MemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CulturalAndEducationVO {
    private int boardNo;
    private String boardTitle;
    private String content;
    private String boardDate;
    private int userCode;
    private String attachedFileName;
    private int boardCnt;
    private int boardType;
    private MemberVO memberVO;
}
