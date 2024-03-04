package com.green.Library.web.board.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.img.vo.ImgVO;

import java.util.List;

public interface BoardService {

    //게시글 작성
    void insertCulBoard(BoardVO boardVO);

    //게시글 리스트
    List<BoardVO> selectCulBoardList();

    //이미지 등록
    void insertImgList(ImgVO imgVO);

    //게시글 큰 값
    int maxBoardNo();


}
