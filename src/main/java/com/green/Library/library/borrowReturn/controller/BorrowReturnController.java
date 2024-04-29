package com.green.Library.library.borrowReturn.controller;

import com.green.Library.library.borrowReturn.service.BorrowReturnService;
import com.green.Library.library.borrowReturn.vo.BookBNRVO;
import com.green.Library.library.borrowReturn.vo.BookReservationVO;
import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.library.user.service.UserService;
import com.green.Library.library.user.vo.SearchUserVO;
import com.green.Library.web.member.vo.MemberVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bookAdmin")
public class BorrowReturnController {
    @Resource(name = "menuService")
    LibraryMenuService libraryMenuService;
    @Resource(name = "borrowReturnService")
    BorrowReturnService borrowReturnService;
    @Resource(name = "userService")
    UserService userService;


    //---------------대출반납----------------------------------------
    //대출 반납
    @GetMapping("/borrowReturn")
    public String goBorrowReturn(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());

        System.out.println("대출반납으로 이동");
        return "content/library/borrowReturn/borrowReturn.html";
    }

    //이용자 조회와 대출반납 실행
    @ResponseBody
    @PostMapping("/selectBorrowInfoFetch")
    public Map<String, Object> selectBorrowInfo(@RequestBody Map<String, String> inputData, BookBNRVO bookBNRVO, BookReservationVO bookReservationVO) {

        // 데이터 담을 통
        Map<String, Object> returnData = new HashMap<>();

        //input 태그에 입력한 데이터
        String cardNumOrBookCode = inputData.get("inputValue");
        //받아온 selectedCardNum이 0이면 선택한 카드번호가 없다는 뜻
        int selectedCardNum = Integer.parseInt(inputData.get("selectedCardNum"));

        //카드번호와 북코드
        int cardNum = 0;
        String bookCode = null;

        // 카드번호가 데이터로 들어 올 때 대출 할 이용자 정보 조회
        if (isNumberic(cardNumOrBookCode) && Integer.parseInt(cardNumOrBookCode) > 0 && !String.valueOf(cardNumOrBookCode).contains("GR")) {

            System.out.println("MAP 데이터@@@@@@@@@@@" + inputData);

            System.out.println("@@@@" + cardNum);

            cardNum = Integer.parseInt(cardNumOrBookCode);
            bookCode = null;

            //카드번호로 회원 정보 조회를 위한 파라메타 세팅
            MemberVO memberInfo = new MemberVO();
            memberInfo.setCardNum(cardNum);

            //카드번호로 회원 정보 조회
            returnData.put("userInfo", borrowReturnService.selectBorrowInfo(memberInfo));
            returnData.put("isReserved", false);
            return returnData;

        }
        //책 번호를 입력했을 경우 카드넘을 통해 이용자 코드를 가져와서 대출
        else if (String.valueOf(cardNumOrBookCode).contains("GR") && selectedCardNum != 0) {

            bookCode = String.valueOf(cardNumOrBookCode);
            cardNum = selectedCardNum;

            //유저코드 조회
            int userCode = borrowReturnService.selectUserCode(cardNum);

            // 예약 내역 데이터 세팅
            bookReservationVO.setBookCode(bookCode);
            bookReservationVO.setUserCode(userCode);

            //가져온 책번호가 유효성 책인지 검사
            //책코드가 유효한 데이터라면
            if (borrowReturnService.isCorrectBookCode(bookCode) && borrowReturnService.selectBookAvailable(bookCode)) {

                //대출 내역을 insert
                bookBNRVO.setBookCode(bookCode);
                bookBNRVO.setUserCode(userCode);
                bookBNRVO.setBorrowDate(inputData.get("borrowDate"));
                borrowReturnService.insertBorrow(bookBNRVO);

                //다시 대출자의 모든 대출 내역을 조회
                MemberVO vo = new MemberVO();
                vo.setCardNum(cardNum);

                //사용자 정보 및 내역 조회
                returnData.put("userInfo", borrowReturnService.selectBorrowInfo(vo));
                returnData.put("isReserved", false);
                return returnData;

            // 예약 내역 대출 진행
            } else if (borrowReturnService.isCorrectBookCode(bookCode) && borrowReturnService.selectBookAvailable(bookCode) && borrowReturnService.selectGetReservation(bookReservationVO)) {

                //대출 내역을 insert
                bookBNRVO.setBookCode(bookCode);
                bookBNRVO.setUserCode(userCode);
                bookBNRVO.setBorrowDate(inputData.get("borrowDate"));
                borrowReturnService.updateGetBorrow(bookBNRVO);

                //다시 대출자의 모든 대출 내역을 조회
                MemberVO vo = new MemberVO();
                vo.setCardNum(cardNum);

                //사용자 정보 및 내역 조회
                returnData.put("userInfo", borrowReturnService.selectBorrowInfo(vo));
                returnData.put("isReserved", true);
                return returnData;
            }
            // 책 번호를 입력하고, 해당 책이 대출 중인 상태일 때 반납 진행
            else if (borrowReturnService.isCorrectBookCode(bookCode) && !borrowReturnService.selectBookAvailable(bookCode)) {

                System.out.println("반납" + inputData);

                // 반납 내역을 update
                bookBNRVO.setReturnDate(inputData.get("returnDate"));
                bookBNRVO.setBookCode(bookCode);
                borrowReturnService.updateReturnInfo(bookBNRVO);

                // 반납 된 책이 예약 내역이 있을 경우 예약 진행
                if (!borrowReturnService.selectChkReservation(bookCode).isEmpty()) {

                    borrowReturnService.updateHasReservation(borrowReturnService.selectChkReservation(bookCode).get(0).getUserCode());

                    returnData.put("isReserved", true);
                }

                //다시 대출자의 모든 대출 내역을 조회
                MemberVO vo = new MemberVO();
                vo.setCardNum(cardNum);

                //사용자 정보 및 내역  조회
                returnData.put("userInfo", borrowReturnService.selectBorrowInfo(vo));
                return returnData;

            } else {
                returnData.put("noData", new MemberVO());
                return returnData;
            }
        }
        // 데이터 형식에 맞게 입력 하지 않았을 경우
        else{

            returnData.put("noData", new MemberVO());
            return returnData;
        }

    }

    // 대출반납 페이지에서 이용자 정보 변경 메소드
    @ResponseBody
    @PostMapping("/updateUserIntroFetch")
    public void updateUserIntro(@RequestBody MemberVO memberVO){

        userService.updateUserIntro(memberVO);

    }

    //일관 반납
    @GetMapping("/consistentReturn")
    public String goConsistentReturn(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("대출반납으로 이동");
        return "content/library/borrowReturn/consistentReturn";
    }

    //대출 반납 관리
    @GetMapping("/borrowReturnManagement")
    public String goBorrowReturnManagement(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());



        System.out.println("대출 반납 관리 이동");
        return "content/library/borrowReturn/borrowReturnManagement";
    }

    //예약 정보 관리
    @RequestMapping("/reservationInfo")
    public String goReservationInfo(Model model, SearchUserVO searchUserVO){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());

        List<BookReservationVO> reserveInfo = borrowReturnService.selectReservationList(searchUserVO);

        model.addAttribute("reserveInfo", reserveInfo);

        System.out.println("예약 정보 관리 이동");

        return "content/library/borrowReturn/reservationInfo";

    }

    //출력 이력 관리
    @GetMapping("/outputHistory")
    public String goOutputHistory(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("출력 이력 관리 이동");
        return "content/library/borrowReturn/outputHistory";
    }

    //매개변수로 넘오언 데이터가 숫자 형식으로 변환가능한지 판단
    public boolean isNumberic(String str) {
        return str.matches("[+-]?\\d*(\\.\\d+)?");
    }
}
