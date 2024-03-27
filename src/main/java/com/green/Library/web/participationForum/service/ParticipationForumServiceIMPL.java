package com.green.Library.web.participationForum.service;

import com.green.Library.web.participationForum.vo.AskAndAnswerBoardVO;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.board.vo.SearchVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
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
    //정렬를 위한 origin_order_num 다음 값 구하기
    @Override
    public int selectMaxOriginOrderNum() {
        return sqlSession.selectOne("participationForumMapper.selectMaxOriginOrderNum");
    }
    //정렬를 위한 ANSWER_ORDER_NUM
//    해당 질문글의 order_num 으로 검색해서 answer_order_num이 0인 경우는 맨처음 답글을 단것
//    0이 아닌 경우에는 해당 질문글에 여러개의 답변이 달린것
    @Override
    public int chkAnswerOrderNum(int originOrderNum) {
        return sqlSession.selectOne("participationForumMapper.chkAnswerOrderNum", originOrderNum);
    }

    //boardNum으로 origin_order_num 찾기
    @Override
    public int searchOriginOrderNumForAnswer(int boardNum) {
        return sqlSession.selectOne("participationForumMapper.searchOriginOrderNumForAnswer",boardNum);
    }

    //묻고 답하기 게시물 넣기
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAskAndAnswerBoard(BoardVO boardVO) {
        sqlSession.insert("participationForumMapper.insertBoard",boardVO); //게시물 먼저 넣고
        sqlSession.insert("boardMapper.insertFileList",boardVO); //첨부파일 넣고
        if(Optional.of(boardVO).map(s ->s.getAskAndAnswerBoardVO()).map(s->s.getIsAnswerBoard()).isPresent()){
            sqlSession.insert("participationForumMapper.insertAskAndAnswerBoard2",boardVO); //값이 있으면 이거실행
            System.out.println("insertAskAndAnswerBoard2 실행");
        }else{
            sqlSession.insert("participationForumMapper.insertAskAndAnswerBoard",boardVO); //널값이면 이거실행
            System.out.println("insertAskAndAnswerBoard1 실행");
        }


    }

    //글 조회하기
    //이거 쓰는걸로
    @Override
    public List<BoardVO> selectAskAndAnswerBoardList(SearchVO searchVO) {
        return sqlSession.selectList("participationForumMapper.selectAskAndAnswerBoardList",searchVO);
    }

    @Override
    public List<BoardVO> selectAnswerBoardList(SearchVO searchVO) {
        return sqlSession.selectList("participationForumMapper.selectAnswerBoardList",searchVO);
    }

    @Override
    public List<BoardVO> selectAskAndAnswerBoardList2(SearchVO searchVO) {
        return sqlSession.selectList("participationForumMapper.selectAskAndAnswerBoardList2",searchVO);
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

    @Override
    public BoardVO detailAskBoard2(int boardNum) {
        return sqlSession.selectOne("participationForumMapper.detailAskBoard",boardNum);
    }


}
