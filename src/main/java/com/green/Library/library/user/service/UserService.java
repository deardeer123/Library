package com.green.Library.library.user.service;

import com.green.Library.library.user.vo.SearchUserVO;
import com.green.Library.web.member.vo.MemberVO;

import java.util.List;

public interface UserService {

    // 이용자 정보 조회
    List<MemberVO> selectUserInfoList(SearchUserVO searchUserVO);

    // 카드번호 부여
    void updateCardNum(MemberVO memberVO);

    // 이용자 상세 정보 조회
    MemberVO showUserDetail(int userCode);
}
