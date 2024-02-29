package com.green.Library.web.participationForum.service;

import com.green.Library.web.participationForum.vo.ParticipationForumVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ParticipationForumService")
public class ParticipationForumServiceIMPL implements ParticipationForumService{

    @Autowired
    private SqlSessionTemplate sqlSession;
    //공지사항작성
    @Override
    public void insertNotice(ParticipationForumVO participationForumVO) {
        sqlSession.insert("participationForumMapper.insertBoard", participationForumVO);
    }
    //공지사항조회
    @Override
    public List<ParticipationForumVO> selectNotice() {
        return sqlSession.selectList("participationForumMapper.selectNotice");
    }
    //조회수증가
    @Override
    public void updateCnt(int boardNo) {
        sqlSession.update("participationForumMapper.updateCnt",boardNo);
    }
    //공지사항 상세조회
    @Override
    public ParticipationForumVO noticeDetail(int boardNo) {
        return sqlSession.selectOne("participationForumMapper.noticeDetail", boardNo);
    }
    //


}
