package com.green.Library.web.participationForum.service;

import com.green.Library.web.participationForum.vo.ParticipationForumVO;

import java.util.List;

public interface ParticipationForumService {
    //공지사항 등록
    void insertNotice (ParticipationForumVO participationForumVO);
    //공지사항 조회
    List<ParticipationForumVO> selctNotice ();
    //조회수증가
    int updateCnt (int boardNo);

}
