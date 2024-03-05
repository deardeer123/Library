package com.green.Library.web.culturalAndEducation.service;

import com.green.Library.web.culturalAndEducation.vo.CulturalAndEducationVO;
import com.green.Library.web.participationForum.vo.ParticipationForumVO;
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


//    @Override
//    public List<CulturalAndEducationVO> selectCulBoardList() {
//        return sqlSession.selectList("culturalAndEducationMapper.selectCulBoardList");
//    }
//
//    @Override
//    @Transactional(rollbackFor = ExportException.class)
//    public void insertCulBoard(CulturalAndEducationVO culturalAndEducationVO) {
//        sqlSession.insert("culturalAndEducationMapper.insertCulBoard",culturalAndEducationVO);
//        sqlSession.insert("culturalAndEducationMapper.insertImgList",culturalAndEducationVO);
//    }
//
//
//    @Override
//    public int isNullBoardNo() {
//        return sqlSession.selectOne("culturalAndEducationMapper.isNullBoardNo");
//    }
//


}
