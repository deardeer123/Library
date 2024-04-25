package com.green.Library.web.findBook.controller;

import com.green.Library.library.borrowReturn.vo.BookReservationVO;
import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.libraryBook.service.LibraryBookService;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import com.green.Library.web.findBook.service.FindBookService;
import com.green.Library.web.findBook.vo.FindBookVO;
import com.green.Library.web.findBook.vo.SearchDateVO;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class FindBookController {
    @Resource(name ="webMenuService")
    WebMenuService webMenuService;

    @Resource(name= "findBookService")
    FindBookService findBookService;

    private final int selectedMenuIndex = 1;

    LibraryBookService libraryBookService;

    @Autowired
    public FindBookController(LibraryBookService libraryBookService){
        this.libraryBookService = libraryBookService;
    }


    @RequestMapping("/findFullBook")
    public String goFindFullBook(Model model, BookSearchVO bookSearchVO, BookReservationVO bookReservationVO , HttpSession session){
        //인터셉터에 notice라는 정보를 넘겨줌
        model.addAttribute("page","findFullBook");

        //페이징
        System.out.println(bookSearchVO.getNowPage());
        bookSearchVO.setNowPage(bookSearchVO.getNowPage());

        //전체 게시물 갯수 설정
        int totalDataCnt = findBookService.selectFindBookCnt(bookSearchVO);
        System.out.println(totalDataCnt);
        bookSearchVO.setTotalDataCnt(totalDataCnt);

        //페이지 정보 세팅
        bookSearchVO.setPageInfo();

        //계속 이상하게 나오길래 넣은 코드입니다.
        if(totalDataCnt == 0){
            bookSearchVO.setEndPage(1);
        }
        //카테고리 정보가 필요한 거 같음
        Optional<List<LibraryBookCategoryVO>> catelist = Optional.ofNullable(libraryBookService.selectCateList());
        model.addAttribute("cateList", catelist.get());

        //책 리스트 보내기
        model.addAttribute("bookList", findBookService.findBookList(bookSearchVO));

        System.out.println("전체자료찾기");

        // 도서 상세보기에서 예약 시 넘겨줄 userCode
//        bookReservationVO.setUserCode((Integer) session.getAttribute("userCode"));
//        model.addAttribute("userCode", bookReservationVO.getUserCode());

        return "content/homePage/findBook/findFullBook";
    }

    
    //책 하나 정보 얻기
    @ResponseBody
    @PostMapping("/findBookDetailFetch")
    public FindBookVO findBookDetail(@RequestParam(name = "bookCode") String bookCode){
        FindBookVO findBookVO = findBookService.findBookOne(bookCode);
        System.out.println(findBookVO);
        return findBookVO;
    }

    // 예약 하기
//    @PostMapping("/bookReservationFetch")
//    public String bookReservationFetch(@RequestParam(name = "bookCode")String bookCode,
//                                        @RequestParam(name = "userCode")int userCode,
//                                        BookReservationVO bookReservationVO){
//
//        bookReservationVO.setUserCode(userCode);
//
//
//        if(userCode != 0){
//            findBookService.bookReservationFetch(bookReservationVO);
//            return "get";
//        } else {
//            return "fail";
//        }
//    }

//    ------------------------------------------------------------------------------------------------
    @RequestMapping("/newBook")
    public String goNewBook(Model model, SearchDateVO searchDateVO){
        //인터셉터에 newBook 정보를 넘겨줌
        model.addAttribute("page","newBook");


        //페이징
        searchDateVO.setNowPage(searchDateVO.getNowPage());

        //전체 게시물 갯수 설정
        int newBookCnt = findBookService.selectNewBookCnt(searchDateVO);
        searchDateVO.setTotalDataCnt(newBookCnt);

        //페이지 정보 세팅
        searchDateVO.setPageInfo();

        //계속 이상하게 나오길래 넣은 코드입니다.
        if(newBookCnt == 0){
            searchDateVO.setEndPage(1);
        }

        //새로들어온 책 -> 등록날짜가 제일 최신인 책들!!
        List<FindBookVO> newBookList = findBookService.selectNewBookList(searchDateVO);
        model.addAttribute("newBookList", newBookList);

        //카테고리 정보가 필요한 거 같음
        Optional<List<LibraryBookCategoryVO>> catelist = Optional.ofNullable(libraryBookService.selectCateList());
        model.addAttribute("cateList", catelist.get());

        return "content/homePage/findBook/newBook";
    }
    @GetMapping("/recommendedBook")
    public String goRecommendedBook(Model model, @RequestParam(name ="userType",defaultValue = "", required = false)String userType){
        //인터셉터에 recommendedBook 정보를 넘겨줌
        model.addAttribute("page","recommendedBook");

        Optional<List<FindBookVO>> recommendedBookList = findBookService.recommendedBookList(userType);
        model.addAttribute("bookList", recommendedBookList.get());


        System.out.println("추천도서");
        return "content/homePage/findBook/recommendedBook";
    }
    
    //대출이 많은책
    @GetMapping("/manyBorrowedBook")
    public String goManyBorrowedBook(Model model){
        //인터셉터에 newBook 정보를 넘겨줌
        model.addAttribute("page","manyBorrowedBook");
        //대출이 많은책 10권 보내주기
        model.addAttribute("bookList", findBookService.manyRentBook());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        System.out.println(sdf.format(now));
        model.addAttribute("date", sdf.format(now) );

        return "content/homePage/findBook/manyBorrowedBook";
    }
    @GetMapping("/hopeBookApplication")
    public String goHopeBookApplication(Model model){
        //인터셉터에 hopeBookApplication 정보를 넘겨줌
        model.addAttribute("page","hopeBookApplication");

        System.out.println("희망도서신청");
        return "content/homePage/findBook/hopeBookApplication";
    }
}
