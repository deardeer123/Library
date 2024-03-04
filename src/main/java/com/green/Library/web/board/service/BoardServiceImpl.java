package com.green.Library.web.board.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
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
        sqlSession.insert("boardMapper.insertImgList",boardVO);
    }


    @Override
    public List<BoardVO> selectBoardList(SearchVO searchVO) {
        return sqlSession.selectList("boardMapper.selectBoardList",searchVO);
    }

    @Override
    public int isNullBoardNo() {
        return sqlSession.selectOne("boardMapper.isNullBoardNo");
    }

    @Override
    public int countBoard() {
        return sqlSession.selectOne("boardMapper.countBoard");
    }

    @Override
    public int maxBoardNo() {
        return sqlSession.selectOne("boardMapper.insertImgList");
    }


}
