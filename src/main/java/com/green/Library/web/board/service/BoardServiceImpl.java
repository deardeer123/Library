package com.green.Library.web.board.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.img.vo.ImgVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public void insertCulBoard(BoardVO boardVO) {
        sqlSession.insert("boardMapper.insertCulBoard",boardVO);
    }

    @Override
    public List<BoardVO> selectCulBoardList() {
        return sqlSession.selectList("boardMapper.selectCulBoardList");
    }

    @Override
    public void insertImgList(ImgVO imgVO) {
        sqlSession.insert("boardMapper.insertImgList",imgVO);
    }

    @Override
    public int maxBoardNo() {
        return sqlSession.selectOne("boardMapper.insertImgList");
    }
}
