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

    //묻고답하기 조회
    List<BoardVO> selectQna();

    //다음에 들어갈 BoardCode 조회
    int selectNextBoardCode();

    //게시글 리스트
    List<BoardVO> forumSelectBoardList(SearchVO searchVO);

    //정렬를 위한 origin_order_num 다음 값 구하기
    int selectMaxOriginOrderNum();

    //정렬를 위한 ANSWER_ORDER_NUM
//    해당 질문글의 order_num 으로 검색해서 answer_order_num이 0인 경우는 맨처음 답글을 단것
//    0이 아닌 경우에는 해당 질문글에 여러개의 답변이 달린것
    int chkAnswerOrderNum(int originOrderNum);

    //boardNum으로 origin_order_num 찾기
    int searchOriginOrderNumForAnswer(int boardNum);

    //묻고 답하기 게시물 만들기
    void insertAskAndAnswerBoard(BoardVO boardVO);

    //묻고 답하게 게시물 조회하기
    List<BoardVO> selectAskAndAnswerBoardList(SearchVO searchVO);
    //묻고 답하기 답변한 글 조회하기
    List<BoardVO> selectAnswerBoardList(SearchVO searchVO);
    //에휴 잘 모르겠다.
    List<BoardVO> selectAskAndAnswerBoardList2(SearchVO searchVO);

    //글 게시물 조회
    int partiCountBoard(SearchVO searchVO);

    //글 게시물 상세보기
    BoardVO detailAskBoard(int boardNum);

    //글 변경하기 위한 상세보기
    BoardVO detailAskBoard2(int boardNum);

}
