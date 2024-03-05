package com.green.Library.web.board.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.img.vo.ImgVO;

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

<<<<<<< HEAD
    //상세 게시판
    BoardVO selectBoardDetail();

    //게시글 리스트
    List<BoardVO> forumSelectBoardList(SearchVO searchVO);

//    //업로드 파일
//    void insertUploadFile (BoardVO boardVO);
=======

    BoardVO selectBoardDetail(int boardNo);

    //조회수
    void boardCntUp(int boardNo);

    // 게시판 삭제
    void deleteBoard(int boardNo);

    //게시판 수정
    void updateBoard(BoardVO boardVO);
>>>>>>> 0bbaaf2eaeeb5977377a82cc91efa83d44d01ef1
