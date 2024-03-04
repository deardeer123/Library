package com.green.Library.web.participationForum.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.participationForum.vo.ParticipationForumVO;

import java.util.List;

public interface
ParticipationForumService {
    //공지사항 등록
    void insertNotice (BoardVO boardVO);

    //공지사항 조회
    List<BoardVO> selectNotice ();

    //조회수증가
    void updateCnt (int boardNo);

    //공지사항 상세조회
    BoardVO noticeDetail (int boardNo);

    //
}
