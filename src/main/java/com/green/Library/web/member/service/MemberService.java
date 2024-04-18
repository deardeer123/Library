package com.green.Library.web.member.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.member.vo.ApplyVO;
import com.green.Library.web.member.vo.MemberVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemberService {


    void insertMember(MemberVO memberVO);

    MemberVO login(String memberId);

    void apply(ApplyVO applyVO);

    List<ApplyVO> applyList();

    List<ApplyVO> selectApplyUser(int userCode);

    List<BoardVO> applyUserBoardList(BoardVO boardVO);

    void deleteApply(int boardNum);

    void CF(int boardNum);

    //userCode로 코드, 아이디, 이름, 전화번호 얻는 메소드
    MemberVO selectMemberInfoToUserCode(int userCode);

    //myPage
    MemberVO myPageUserInfo(int userCode);

}

