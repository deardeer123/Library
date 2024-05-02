package com.green.Library.library.user.service;

import com.green.Library.library.user.vo.SearchUserVO;
import com.green.Library.web.member.vo.MemberVO;

import java.util.List;

public interface UserService {

    // 이용자 정보 조회
    List<MemberVO> selectUserInfoList(SearchUserVO searchUserVO);

    // 카드번호 부여
    int updateCardNum(MemberVO memberVO);

    // 이용자 상세 정보 조회
    MemberVO showUserDetail(int userCode);

    // 이용자 상세 정보 수정
    void userDetailUpdate(MemberVO memberVO);

    // 대출반납에 조회 된 이용자의 이용자 정보 변경
    void updateUserIntro(MemberVO memberVO);

    // userCode를 이용하여 cardNum 조회
    int selectCardNum(int userCode);

}
