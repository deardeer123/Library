package com.green.Library.library.message.controller;


import com.green.Library.web.member.service.MemberServiceImpl;
import com.green.Library.web.member.vo.MemberVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.StorageType;
import net.nurigo.sdk.message.request.MessageListRequest;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.MessageListResponse;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MessageController {

    final DefaultMessageService messageService;

    @Resource
    private MemberServiceImpl memberService;

    public MessageController() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize("NCSLIP46VWCR4LXA", "9TA5XGWAGNRNRRIWHNUUQQQXMLLL8PFA", "https://api.coolsms.co.kr");
    }

    /**
     * 단일 메시지 발송 예제
     */

    @PostMapping("/FindOfTel")
    public String sendOne(MemberVO memberVO) {

        MemberVO memberVO1 = memberService.findUser(memberVO);
        memberVO1.setEmail(memberVO1.getEmail().substring(0, memberVO1.getEmail().indexOf("@")));
        System.out.println(memberVO1);
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.

        //발신자
        message.setFrom("01041505657");
        //수신자
        message.setTo(memberVO.getUserTel());
        //보낼 메세지
        message.setText("[그린도서관] \n"+memberVO.getUserName()+"님의 아이디는 : " + memberVO1.getUserId() + "입니다 \n 개인정보유출 방지를 위해 개인정보 수정을 부탁드립니다.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return "redirect:/login";
    }


}
