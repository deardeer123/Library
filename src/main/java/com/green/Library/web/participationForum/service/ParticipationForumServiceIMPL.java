package com.green.Library.web.participationForum.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("ParticipationForumService")
public class ParticipationForumServiceIMPL implements ParticipationForumService{

    @Autowired
    private SqlSessionTemplate sqlSession;

    //공지사항작성
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public void insertNotice(BoardVO boardVO) {
        sqlSession.insert("participationForumMapper.insertBoard", boardVO);
        sqlSession.insert("participationForumMapper.insertUploadFile", boardVO);
    }

    //공지사항조회
    @Override
    public List<BoardVO> selectNotice() {
        return sqlSession.selectList("participationForumMapper.selectNotice");
    }

    //조회수증가
    @Override
    public void updateCnt(int boardNo) {
        sqlSession.update("participationForumMapper.updateCnt",boardNo);
    }

    //공지사항 상세조회
    @Override
    public BoardVO noticeDetail(int boardNo) {
        return sqlSession.selectOne("participationForumMapper.noticeDetail", boardNo);
    }

    @Override
    public List<BoardVO> selectQna() {
        return sqlSession.selectList("participationForumMapper.selectQna");
    }

    @Override
    public int selectNextBoardCode() {
        return sqlSession.selectOne("participationForumMapper.selectNextBoardCode");
    }

    @Override
    public List<BoardVO> forumSelectBoardList(SearchVO searchVO) {
        return sqlSession.selectList("participationForumMapper.forumSelectBoardList",searchVO);
    }
    //묻고 답하기 게시물 넣기
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAskAndAnswerBoard(BoardVO boardVO) {
        sqlSession.insert("participationForumMapper.insertBoard",boardVO); //게시물 먼저 넣고
        sqlSession.insert("boardMapper.insertFileList",boardVO); //첨부파일 넣고
        sqlSession.insert("participationForumMapper.insertAskAndAnswerBoard",boardVO); //묻고 답하기 넣고
    }

    //글 조회하기
    @Override
    public List<BoardVO> selectAskAndAnswerBoardList(SearchVO searchVO) {
        return sqlSession.selectList("participationForumMapper.selectAskAndAnswerBoardList",searchVO);
    }

    //글 갯수 찾기
    @Override
    public int partiCountBoard(SearchVO searchVO) {
        return sqlSession.selectOne("participationForumMapper.partiCountBoard",searchVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BoardVO detailAskBoard(int boardNum) {
        sqlSession.update("participationForumMapper.boardCntUp",boardNum);
        return sqlSession.selectOne("participationForumMapper.detailAskBoard",boardNum);
    }


}
