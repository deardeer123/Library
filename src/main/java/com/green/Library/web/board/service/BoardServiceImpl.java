package com.green.Library.web.board.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.PlusVO;
import com.green.Library.web.board.vo.SearchVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.server.ExportException;
import java.util.List;

@Service("boardService")
public class BoardServiceImpl implements BoardService {
    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    @Transactional(rollbackFor = ExportException.class)
    public void insertCulBoard(BoardVO boardVO) {
        sqlSession.insert("boardMapper.insertCulBoard", boardVO);
        sqlSession.insert("boardMapper.insertFileList", boardVO);
    }


    @Override
    public List<BoardVO> selectBoardList(SearchVO searchVO) {
        return sqlSession.selectList("boardMapper.selectBoardList", searchVO);
    }


    @Override
    public int isNullBoardNo() {
        return sqlSession.selectOne("boardMapper.isNullBoardNo");
    }

    @Override
    public int countBoard() {
        return sqlSession.selectOne("boardMapper.countBoard");
    }

//    @Override
//    public int maxBoardNo() {
//        return sqlSession.selectOne("boardMapper.insertImgList");
//    }

    public BoardVO selectBoardDetail() {
        return sqlSession.selectOne("boardMapper.selectBoardDetail");
    }



//    @Override
//    public void insertUploadFile(BoardVO boardVO) {
//        sqlSession.insert("boardMapper.insertUpload");
//    }
    public BoardVO selectBoardDetail(int boardNo) {
        return sqlSession.selectOne("boardMapper.selectBoardDetail", boardNo);
    }

    @Override
    public void boardCntUp(int boardNo) {
        sqlSession.update("boardMapper.boardCntUp",boardNo);
    }

    @Override
    public void deleteBoard(int boardNo) {
        sqlSession.delete("boardMapper.deleteBoard",boardNo);
    }

    @Override
    public void updateBoard(BoardVO boardVO) {
        sqlSession.update("boardMapper.updateBoard",boardVO);
    }

    @Override
    @Transactional(rollbackFor = ExportException.class)
    public void selectDeletes(BoardVO boardVO) {
        sqlSession.delete("boardMapper.selectDeleteFiles", boardVO);
        sqlSession.delete("boardMapper.selectDeletes",boardVO);
    }

    @Override
    public List<BoardVO> selectPlusList(SearchVO searchVO) {
        return sqlSession.selectList("boardMapper.selectPlusList", searchVO);
    }

    @Override
    @Transactional(rollbackFor = ExportException.class)
    public void insertParticipation(BoardVO boardVO) {
        sqlSession.insert("boardMapper.insertParticipation",boardVO);
        sqlSession.insert("boardMapper.insertParticipationPlus",boardVO);
        sqlSession.insert("boardMapper.insertEventFileList", boardVO);
    }


}


