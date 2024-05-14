package com.green.Library.web.member.service;

import com.green.Library.library.borrowReturn.vo.BookBNRVO;
import com.green.Library.library.borrowReturn.vo.BookReservationVO;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.member.vo.ApplyVO;
import com.green.Library.web.member.vo.MemberVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemberService {


    void insertMember(MemberVO memberVO);

    MemberVO login(String memberId);

    void apply(ApplyVO applyVO);

    List<ApplyVO> applyList();

    List<ApplyVO> selectApplyUser(int userCode);

    List<BoardVO> applyUserBoardList(BoardVO boardVO);

    void deleteApply(int boardNum);

    void CF(int boardNum);

    //userCode로 코드, 아이디, 이름, 전화번호 얻는 메소드
    MemberVO selectMemberInfoToUserCode(int userCode);

    //myPage
    MemberVO myPageUserInfo(int userCode);

    //회원 정보 변경
    void updateUserInfo(MemberVO memberVO);

    void updateUserPw(MemberVO memberVO);

    MemberVO findUser(MemberVO memberVO);

    MemberVO findPwUser(MemberVO memberVO);

    // 마이페이지 대출반납 내역 조회
    List<BookBNRVO> selectMyHistory(int userCode);

    ///////////////// 예약 관리////////////////////

    // 예약 정보 조회
    List<BookReservationVO> selectMyReservation(int userCode);

    // 예약 본인 취소
    void updateSelfCancel(BookReservationVO bookReservationVO);

    // 예약 자동 취소
    void updateAutoCancel(BookReservationVO bookReservationVO);
}

