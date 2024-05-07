package com.green.Library.library.libraryhome.service;

import com.green.Library.library.libraryhome.vo.CalendarVO;
import com.green.Library.library.libraryhome.vo.MemoPageVO;
import com.green.Library.library.libraryhome.vo.MemoSearchVO;
import com.green.Library.library.libraryhome.vo.MemoVO;
import com.green.Library.libraryMember.vo.LibraryMemberVO;
import com.green.Library.web.board.vo.SearchVO;
import com.green.Library.web.member.vo.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("libraryHomeService")
public class LibraryHomeServiceImpl implements LibraryHomeService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    // 관리자 로그인
    @Override
    public MemberVO login(MemberVO memberVO) {
        return sqlSession.selectOne("memberMapper.login", memberVO);
    }

    @Override
    public List<CalendarVO> selectCalendarList() {
        return sqlSession.selectList("libraryHomeMapper.selectCalendarList");
    }

    @Override
    public void insertCalendar(CalendarVO calendarVO) {
        sqlSession.insert("libraryHomeMapper.insertCalendar",calendarVO);
    }

    @Override
    public void deleteCalendar(CalendarVO calendarVO) {
        sqlSession.delete("deleteCalendar",calendarVO);
    }

//    메모 작성
    @Override
    public void insertMemo(MemoVO memoVO) {
        sqlSession.insert("libraryHomeMapper.insertMemo",memoVO);
    }
//메모 갯수
    @Override
    public int selectMemoCount(MemoSearchVO memoSearchVO) {
        return sqlSession.selectOne("libraryHomeMapper.selectMemoCount",memoSearchVO);
    }

    //메모조회 3개만
    @Override
    public List<MemoVO> selectMemoList3() {
        return sqlSession.selectList("libraryHomeMapper.selectMemoList3");
    }

    //메모조회 다
    @Override
    public List<MemoVO> selectMemoList(MemoSearchVO searchVO) {

        return sqlSession.selectList("libraryHomeMapper.selectMemoList",searchVO);
    }

    //메모 하나만
    @Override
    public MemoVO selectMemoInfo(int id) {
        return sqlSession.selectOne("libraryHomeMapper.selectMemoInfo",id);
    }

    //메모 삭제
    @Override
    public void deleteMemo(int id) {
        sqlSession.delete("libraryHomeMapper.deleteMemo",id);
    }
}
