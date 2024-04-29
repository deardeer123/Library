package com.green.Library.web.member.service;

import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.member.vo.ApplyVO;
import com.green.Library.web.member.vo.MemberVO;
import org.codehaus.groovy.transform.trait.Traits;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberService")
public class MemberServiceImpl implements MemberService{
    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public void insertMember(MemberVO memberVO) {
        sqlSession.insert("memberMapper.insertMember",memberVO);
    }

    @Override
    public MemberVO login(String memberId) {
        return sqlSession.selectOne("memberMapper.login",memberId);
    }

    @Override
    public void apply(ApplyVO applyVO) {
        sqlSession.insert("memberMapper.apply",applyVO);
    }

    @Override
    public List<ApplyVO> applyList() {
        return sqlSession.selectList("memberMapper.applyList");
    }

    @Override
    public List<ApplyVO> selectApplyUser(int userCode) {
        return sqlSession.selectList("memberMapper.selectApplyUser",userCode);
    }

    @Override
    public List<BoardVO> applyUserBoardList(BoardVO boardVO) {
        return sqlSession.selectList("memberMapper.applyUserBoardList",boardVO);
    }

    @Override
    public void deleteApply(int boardNum) {
        sqlSession.delete("memberMapper.deleteApply", boardNum);
    }

    @Override
    public void CF(int boardNum) {
        sqlSession.update("memberMapper.CF",boardNum);
    }

    //userCode로 코드, 아이디, 이름, 전화번호 얻는 메소드
    @Override
    public MemberVO selectMemberInfoToUserCode(int userCode) {
        return sqlSession.selectOne("memberMapper.selectMemberInfoToUserCode",userCode);
    }

    @Override
    public MemberVO myPageUserInfo(int userCode) {
        return sqlSession.selectOne("memberMapper.myPageUserInfo",userCode);
    }

    @Override
    public void updateUserInfo(MemberVO memberVO) {
        sqlSession.update("memberMapper.updateUserInfo",memberVO);
    }

    @Override
    public void updateUserPw(MemberVO memberVO) {
        sqlSession.update("memberMapper.updateUserPw",memberVO);
    }

    //12자리의 랜덤 비밀번호 생성
    public String createRandomPw(){
        String[] charSet = new String[]{
                "0","1","2","3","4","5","6","7","8","9",
                "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
                "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
        };
        String imsiPw = "";
        for (int i = 0; i < 12; i++){
            int randomIndex = (int)(Math.random() * charSet.length);
            imsiPw += charSet[randomIndex];
        }
        return imsiPw;
    }
}
