package com.green.Library.web.culturalAndEducation.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;

import java.util.List;

public interface CulturalAndEducationService {

    //게시글 작성
    //이미지 등록
    void culInsertBoard(BoardVO boardVO);

    //게시글 리스트
    List<BoardVO> culSelectBoardList(SearchVO searchVO);

    //게시글 큰 값
    int culIsNullBoardNo();

    int culCountBoard();


    BoardVO culSelectBoardDetail(BoardVO boardVO);

    //조회수 증가
    void culBoardCntUp(BoardVO boardVO);

    // 게시판 삭제
    void culDeleteBoard(BoardVO boardVO);

    //게시판 수정
    void culUpdateBoard(BoardVO boardVO);

    //이벤트 게시판 작성문
    void insertEventBoard(BoardVO boardVO);

    //이벤트 게시판 리스트
    List<BoardVO> selectEventBoard(SearchVO searchVO);

}
