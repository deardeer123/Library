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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MessageController {

    final DefaultMessageService messageService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Resource
    private MemberServiceImpl memberService;

    public MessageController() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize("NCSLIP46VWCR4LXA", "9TA5XGWAGNRNRRIWHNUUQQQXMLLL8PFA", "https://api.coolsms.co.kr");
    }

    /**
     * 단일 메시지 발송 예제
     */
    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam(name="setTo")String setTo,
                              @RequestParam(name="setFrom")String setFrom,
                              @RequestParam(name="setText")String setText){
        System.out.println("setTo = " + setTo + ", setFrom = " + setFrom + ", setText = " + setText);
        Message message = new Message();

        //발신자
        message.setFrom(setFrom);
        //수신자
        message.setTo(setTo);
        //보낼 매세지
        message.setText(setText);
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        return "redirect:/bookAdmin/home";
    }

    @PostMapping("/FindOfTel")
    public String sendOne(MemberVO memberVO) {

        MemberVO memberVO1 = memberService.findUser(memberVO);
        memberVO1.setEmail(memberVO1.getEmail().replace(",", memberVO1.getEmail()));
        System.out.println(memberVO1);
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.

        //발신자
        message.setFrom("01041505657");
        //수신자
        message.setTo(memberVO.getUserTel());
        //보낼 메세지
        message.setText("[그린도서관] \n"+memberVO.getUserName()+"님의 아이디는 : [" + memberVO1.getUserId() + "] 입니다 \n 개인정보유출 방지를 위해 개인정보 수정을 부탁드립니다.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return "redirect:/login";
    }

    @PostMapping("/pwFindOfTel")
    public String pwFindOfTel(MemberVO memberVO,
                              @RequestParam(name = "userTel") String userTel) {
        //임시 비밀번호 생성
        String randomPw = memberService.createRandomPw();

        //아이디 확인 및 업데이트를 위한 유저코드 유저코드 찾기
        MemberVO member = memberService.findPwUser(memberVO);
        member.setUserCode(member.getUserCode());
        member.setUserTel(userTel);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@랜덤 비밀번호 : "+randomPw);
        //암호화
        String encodedPw = encoder.encode(randomPw);
        //vo에 담고
        member.setUserPw(encodedPw);
        //암호화된 임시비밀번호를 업데이트
        memberService.updateUserPw(member);
        member.setEmail(member.getEmail().replace(",", member.getEmail()));
        System.out.println(member);
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.

        //발신자
        message.setFrom("01041505657");
        //수신자
        message.setTo(memberVO.getUserTel());
        //보낼 메세지
        message.setText("[그린도서관] \n"+memberVO.getUserName()+"님의 임시비밀번호 : [" + randomPw + "] 입니다 \n 개인정보유출 방지를 위해 개인정보 수정을 부탁드립니다.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return "redirect:/login";
    }

}
