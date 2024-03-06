package com.green.Library.web.culturalAndEducation.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.culturalAndEducation.vo.CulturalAndEducationVO;
import com.green.Library.web.participationForum.vo.ParticipationForumVO;

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

//    //업로드 파일
//    void insertUploadFile (BoardVO boardVO);

    BoardVO culSelectBoardDetail(int boardNo);

    //조회수 증가
    void culBoardCntUp(int boardNo);

    // 게시판 삭제
    void culDeleteBoard(int boardNo);

    //게시판 수정
    void culUpdateBoard(BoardVO boardVO);
}
