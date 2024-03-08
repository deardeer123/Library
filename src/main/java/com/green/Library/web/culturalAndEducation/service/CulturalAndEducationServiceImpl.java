package com.green.Library.web.culturalAndEducation.service;

import com.green.Library.web.board.vo.BoardPlusVO;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.server.ExportException;
import java.util.List;

@Service("culturalAndEducationService")
public class CulturalAndEducationServiceImpl implements CulturalAndEducationService{
    @Autowired
    private SqlSessionTemplate sqlSession;


    @Override
    @Transactional(rollbackFor = ExportException.class)
    public void culInsertBoard(BoardVO boardVO) {
        sqlSession.insert("culturalAndEducationMapper.culInsertBoard", boardVO);
        sqlSession.insert("culturalAndEducationMapper.culInsertFileList", boardVO);
    }

    @Override
    public List<BoardVO> culSelectBoardList(SearchVO searchVO) {
        return sqlSession.selectList("culturalAndEducationMapper.culSelectBoardList", searchVO);
    }

    @Override
    public int culIsNullBoardNo() {
        return sqlSession.selectOne("culturalAndEducationMapper.culIsNullBoardNo");
    }

    @Override
    public int culCountBoard() {
        return sqlSession.selectOne("culturalAndEducationMapper.culCountBoard");
    }


    @Override
    public BoardVO culSelectBoardDetail(int boardNo) {
        return sqlSession.selectOne("culturalAndEducationMapper.culSelectBoardDetail", boardNo);
    }

    @Override
    public void culBoardCntUp(int boardNo) {
        sqlSession.update("culturalAndEducationMapper.culBoardCntUp", boardNo);
    }

    @Override
    public void culDeleteBoard(BoardVO boardVO) {
        sqlSession.delete("culturalAndEducationMapper.culDeleteBoard",boardVO);
    }

    @Override
    public void culUpdateBoard(BoardVO boardVO) {
        sqlSession.update("culturalAndEducationMapper.culUpdateBoard",boardVO);
    }

    @Override
    @Transactional(rollbackFor = ExportException.class)
    public void insertEventBoard(BoardVO boardVO) {
        sqlSession.insert("culturalAndEducationMapper.culInsertBoard", boardVO);
        sqlSession.insert("culturalAndEducationMapper.insertEventBoard", boardVO);
        sqlSession.insert("culturalAndEducationMapper.culInsertFileList", boardVO);
    }

    @Override
    public List<BoardVO> selectEventBoard(SearchVO searchVO) {
        return sqlSession.selectList("culturalAndEducationMapper.selectEventBoard", searchVO);
    }
}
