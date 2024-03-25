package com.green.Library.web.member.service;

import com.green.Library.web.member.vo.MemberVO;
import org.springframework.stereotype.Service;

public interface MemberService {


    void insertMember(MemberVO memberVO);

    MemberVO login(String memberId);




}

