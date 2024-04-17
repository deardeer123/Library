package com.green.Library.web.findBook.controller;

import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.web.findBook.service.FindBookService;
import com.green.Library.web.findBook.vo.FindBookVO;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/")
public class FindBookController {
    @Resource(name ="webMenuService")
    WebMenuService webMenuService;

    @Resource(name= "findBookService")
    FindBookService findBookService;

    private final int selectedMenuIndex = 1;


    @RequestMapping("/findFullBook")
    public String goFindFullBook(Model model, BookSearchVO bookSearchVO){
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

        //책 리스트 보내기
        model.addAttribute("bookList", findBookService.findBookList(bookSearchVO));


        System.out.println("전체자료찾기");

        return "content/homePage/findBook/findFullBook";
    }

    
    //책 하나 정보 얻기
    @ResponseBody
    @PostMapping("/findBookDetail")
    public FindBookVO findBookDetail(@RequestParam(name = "bookCode") String bookCode){
        FindBookVO findBookVO = findBookService.findBookOne(bookCode);
        System.out.println(findBookVO);
        return findBookVO;
    }

//    ------------------------------------------------------------------------------------------------
    @GetMapping("/newBook")
    public String goNewBook(Model model){

        //인터셉터에 newBook 정보를 넘겨줌
        model.addAttribute("page","newBook");

        System.out.println("새로 들어온 책");
        return "content/homePage/findBook/newBook";
    }
    @GetMapping("/recommendedBook")
    public String goRecommendedBook(Model model){
        //인터셉터에 recommendedBook 정보를 넘겨줌
        model.addAttribute("page","recommendedBook");


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
