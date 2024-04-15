package com.green.Library.web.board.service;

import com.green.Library.web.board.vo.*;
import com.green.Library.web.member.vo.ApplyVO;
import com.green.Library.web.member.vo.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.server.ExportException;
import java.util.List;
import java.util.Optional;

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
    public int countBoard(int boardType) {
        return sqlSession.selectOne("boardMapper.countBoard", boardType);
    }


    public BoardVO selectBoardDetail() {
        return sqlSession.selectOne("boardMapper.selectBoardDetail");
    }



    public BoardVO selectBoardDetail(int boardNum) {
        return sqlSession.selectOne("boardMapper.selectBoardDetail", boardNum);
    }

    @Override
    public void boardCntUp(int boardNum) {
        sqlSession.update("boardMapper.boardCntUp",boardNum);
    }

    @Override
    public void deleteBoard(int boardNum) {
        sqlSession.delete("boardMapper.deleteBoard",boardNum);
    }

    @Override
    public void updateBoard(BoardVO boardVO) {
        sqlSession.update("boardMapper.updateBoard",boardVO);
    }

    @Override
    @Transactional(rollbackFor = ExportException.class)
    public void selectDeletes(BoardVO boardVO) {
        sqlSession.delete("boardMapper.selectDeleteFiles", boardVO);
        sqlSession.delete("boardMapper.selectBPDeletes",boardVO);
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

    @Override
    public BoardVO selectEventBoardDetail(int boardNum) {
        return sqlSession.selectOne("boardMapper.selectEventBoardDetail", boardNum);
    }

    @Override
    @Transactional(rollbackFor = ExportException.class)
    public void updateEventBoardDetail(BoardVO boardVO) {
        sqlSession.update("boardMapper.updateEventBoardDetail",boardVO);
    }

    @Override
    @Transactional(rollbackFor = ExportException.class)
    public void eventBoardDelete(int boardNum) {
        sqlSession.delete("boardMapper.eventFileDelete", boardNum);
        sqlSession.delete("boardMapper.eventBPDelete", boardNum);
        sqlSession.delete("boardMapper.eventBoardDelete",boardNum);
    }

    @Override
    public void updateStatus() {
        sqlSession.update("boardMapper.updateStatus");
    }

    @Override
    public List<BoardVO> applyBoardList() {
        return sqlSession.selectList("boardMapper.applyBoardList");
    }

    @Override
    public void upPersonnel(int boardNum) {
        sqlSession.update("boardMapper.upPersonnel", boardNum);
    }

    @Override
    public int applyCheck(ApplyVO applyVO) {
        return sqlSession.selectOne("boardMapper.applyCheck",applyVO);
    }

    @Override
    public List<BoardVO> selectMovieList(SearchVO searchVO) {
        return sqlSession.selectList("boardMapper.selectMovieList", searchVO);
    }

    @Override
    @Transactional(rollbackFor = ExportException.class)
    public void insertMovie(BoardVO boardVO) {
        sqlSession.insert("boardMapper.insertMovieBoard",boardVO);
        sqlSession.insert("boardMapper.insertMovie", boardVO);
        sqlSession.insert("boardMapper.insertMovieFile", boardVO);
    }

    @Override
    public BoardVO selectMovieDetail(int boardNum) {
        return sqlSession.selectOne("boardMapper.selectMovieDetail", boardNum);
    }

    @Override
    public void updateMovie(BoardVO boardVO) {
        sqlSession.update("boardMapper.updateMovie",boardVO);

    }

    @Override
    public void updateFile(UploadVO uploadVO) {
        sqlSession.update("boardMapper.updateFile",uploadVO);
    }

    @Override
    public void movieDelete(int boardNum) {
        sqlSession.delete("boardMapper.movieDelete",boardNum);
        sqlSession.delete("boardMapper.eventFileDelete", boardNum);
        sqlSession.delete("boardMapper.eventBoardDelete",boardNum);
    }


}


