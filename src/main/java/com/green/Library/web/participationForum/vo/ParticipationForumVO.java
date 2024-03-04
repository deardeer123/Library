package com.green.Library.web.participationForum.vo;

import com.green.Library.web.member.vo.MemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;
import java.util.List;

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
    private MemberVO memberVO;
    private List<Image> images;

}
