package com.green.Library.web.participationForum.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;

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

    //게시물 갯수 조회
    int partiCountBoard(SearchVO searchVO);

    //묻고답하기 조회
    List<BoardVO> selectQna();

    //다음에 들어갈 BoardCode 조회
    int selectNextBoardCode();

    //게시글 리스트
    List<BoardVO> forumSelectBoardList(SearchVO searchVO);

}
