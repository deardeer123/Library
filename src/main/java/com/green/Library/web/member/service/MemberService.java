package com.green.Library.web.member.service;

import com.green.Library.web.member.vo.ApplyVO;
import com.green.Library.web.member.vo.MemberVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemberService {


    void insertMember(MemberVO memberVO);

    MemberVO login(String memberId);

    void apply(ApplyVO applyVO);

    List<ApplyVO> applyList();

    //userCode로 코드, 아이디, 이름, 전화번호 얻는 메소드
    MemberVO selectMemberInfoToUserCode(int userCode);

}

