package com.green.Library.web.board.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.PlusVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.member.vo.MemberVO;
import org.codehaus.groovy.transform.sc.transformers.CompareIdentityExpression;


import java.util.List;

public interface BoardService {

    //게시글 작성
    //이미지 등록
    void insertCulBoard(BoardVO boardVO);

    //게시글 리스트
    List<BoardVO> selectBoardList(SearchVO searchVO);

    //게시글 큰 값
    int isNullBoardNo();

    //게시판 개수
    int countBoard();

    //상세 게시판
    BoardVO selectBoardDetail();


    BoardVO selectBoardDetail(int boardNo);

    //조회수
    void boardCntUp(int boardNo);

    // 게시판 삭제
    void deleteBoard(int boardNo);

    //게시판 수정
    void updateBoard(BoardVO boardVO);

    //선택 삭제
    //선택 이미지 삭제
    void selectDeletes(BoardVO boardVO);

    List<BoardVO> selectPlusList(SearchVO searchVO);

    //이벤트 게시판
    void insertParticipation(BoardVO boardVO);

    //이벤트 상세보기
    BoardVO selectEventBoardDetail(int boardNum);

    //이벤트 게시물 수정
    void updateEventBoardDetail(BoardVO boardVO);

    void eventBoardDelete(int boardNum);

    //진행상태 변경
    void updateStatus();

    List<BoardVO> applyBoardList();

    void upPersonnel(int boardNum);


}

