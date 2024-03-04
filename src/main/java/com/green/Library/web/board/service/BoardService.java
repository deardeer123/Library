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
}
