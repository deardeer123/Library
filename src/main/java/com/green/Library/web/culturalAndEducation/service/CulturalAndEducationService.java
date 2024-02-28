package com.green.Library.web.culturalAndEducation.service;

import com.green.Library.web.culturalAndEducation.vo.CulturalAndEducationVO;
import com.green.Library.web.participationForum.vo.ParticipationForumVO;

import java.util.List;

public interface CulturalAndEducationService {

    //문화 게시판 리스트
    List<CulturalAndEducationVO> selectCulBoardList();

    //문화 게시판 등록
    void insertCulBoard(CulturalAndEducationVO culturalAndEducationVO);
}
