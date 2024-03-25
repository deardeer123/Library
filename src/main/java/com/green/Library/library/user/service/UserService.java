package com.green.Library.library.user.service;

import com.green.Library.library.user.vo.SearchUserVO;
import com.green.Library.web.member.vo.MemberVO;

import java.util.List;

public interface UserService {

    List<MemberVO> selectUserInfoList(SearchUserVO searchUserVO);

    void updateCardNum(MemberVO memberVO);
}
