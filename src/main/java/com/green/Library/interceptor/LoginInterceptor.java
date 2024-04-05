package com.green.Library.interceptor;

import com.green.Library.web.board.service.BoardServiceImpl;
import com.green.Library.web.board.vo.BoardVO;
import com.green.Library.web.member.service.MemberServiceImpl;
import com.green.Library.web.member.vo.ApplyVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LoginInterceptor implements HandlerInterceptor {
    @Resource(name = "boardService")
    private BoardServiceImpl boardService;
    @Resource(name = "memberService")
    private MemberServiceImpl memberService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //유저
        //memberService.selectApplyUser()
//      //session에서 userCode 가져오는 코드
        Optional<HttpSession> optionalHttpSession = Optional.ofNullable(request.getSession());
        int userCode = optionalHttpSession.map(s->s.getAttribute("userCode")).map(s->(Integer)s).orElse(0);

        //계속 modelAndView가 null 오류떠서 적은 코드
        ModelAndView modelAndView1 = Optional.ofNullable(modelAndView).orElse(new ModelAndView());

        List<ApplyVO> applyVOList = memberService.selectApplyUser(userCode);
        modelAndView1.addObject("applyUser",applyVOList);

        System.out.println("login인터셉터");
        
//      //관리자
        List<BoardVO> boardVOList = boardService.applyBoardList();
        modelAndView1.addObject("apply",boardVOList);
    }
}
