package com.green.Library.library.buy.controller;

import com.green.Library.library.buy.service.BuyService;
import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.library.regAndView.service.RegAndViewService;
import com.green.Library.libraryBook.service.LibraryBookService;
import com.green.Library.libraryBook.vo.LibraryBookBreakageVO;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import com.green.Library.libraryBook.vo.LibraryBookInfoVO;
import com.green.Library.libraryBook.vo.LibraryBookVO;
import com.green.Library.util.ConstantVariable;
import com.green.Library.util.UploadUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller


@RequestMapping("/bookAdmin")
public class BuyController {
    //메뉴 불러올려고 적은거임
    @Resource(name = "menuService")
    private LibraryMenuService libraryMenuService;

    //카테고리 및 뭐 책에 관련된 정보 끌고 올려고 준비햇음
    @Resource(name = "libraryBookService")
    private LibraryBookService libraryBookService;

    @Resource(name = "buyService")
    private BuyService buyService;

    @Resource(name ="regAndViewService")
    private RegAndViewService regAndViewService;


    //----------구입------------
    //희망 자료
    @GetMapping("/wishBook")
    public String goWishBook(Model model){
        //인터셉터로 넘겨줄 page
        model.addAttribute("page", "wishBook");
        //상대방이 원하는 책을 등록하는것

        System.out.println("희망 자료 이동");
        return "content/library/buy/wishBook";
    }
    //삭제 자료
    @GetMapping("/deleteBook")
    public String goDeleteBook(Model model , BookSearchVO bookSearchVO){
        //인터셉터로 넘겨줄 page
        model.addAttribute("page", "deleteBook");


        //제외한 책 갯수 설정
        bookSearchVO.setTotalDataCnt(buyService.selectCntLibraryBookBreakage(bookSearchVO));
        //페이지 정보 세팅
        bookSearchVO.setPageInfo();

        //리스트 넘기기
        List<LibraryBookBreakageVO> bookBreakageList = buyService.selectLibraryBookBreakageList(bookSearchVO);
        model.addAttribute("bookBreakageList" , bookBreakageList);
        System.out.println(bookBreakageList);

        System.out.println("삭제 자료 이동");
        return "content/library/buy/deleteBook";
    }

    @ResponseBody
    @PostMapping("/bookBreakageDetail")
    public Map<String, Object> bookBreakageDetail(@RequestParam(name ="bookCode")String bookCode){
        //제외한 책 정보
        LibraryBookBreakageVO libraryBookBreakageVO = buyService.searchBookBreakageDetail(bookCode);
        //카테고리
        LibraryBookCategoryVO libraryBookCategoryVO = buyService.selectCateNameOne(bookCode);

        Map<String, Object> bookInfo = new HashMap<>();
        bookInfo.put("libraryBookBreakageVO",libraryBookBreakageVO);
        bookInfo.put("libraryBookCategoryVO", libraryBookCategoryVO);
        return bookInfo;
    }

    @ResponseBody
    @PostMapping("/bookRedisplay")
    public void bookRedisplay(@RequestParam(name="bookCode")String bookCode){
        System.out.println(bookCode);
    }




    @GetMapping("/buyBook")
    public String goBuyBook(Model model, @RequestParam(name="insert" , required = false, defaultValue = "0") int insert){
        //인터셉터로 넘겨줄 page
        model.addAttribute("page", "buyBook");

        //가기전에 카테고리 목록 리스트 가져오기
        model.addAttribute("cateList",libraryBookService.selectCateList());

        System.out.println(insert);
        model.addAttribute("insert", insert);
        System.out.println("구입 자료 이동");
        return "content/library/buy/buyBook";
    }

    @PostMapping("regBook")
    public String goRegBook(LibraryBookVO libraryBookVO,
                            @RequestParam(name="bookImg")MultipartFile bookImg ,
                            @RequestParam(name="bookIntro")String bookIntro,
                            @RequestParam(name="bookCateCode")int bookCateCode ,
                            @RequestParam(name="bookMidCateCode")int bookMidCateCode){

        //일단 넣어야할 코드 잘 가져오는지 확인좀 합시다.
        //이건 넣어야할 bookCode를 구하는 코드입니다.
        System.out.println(libraryBookService.searchMaxCode());

        System.out.println(bookIntro);
        System.out.println(libraryBookVO);
        System.out.println(bookImg);
        System.out.println(bookCateCode);
        System.out.println("------------------------------");

        //일단 손이 가는대로 적음
        //만약에 사진이 올라가지 않으면 디폴트 사진을 넣을려고 새로운 객체를 생성했음
        LibraryBookInfoVO libraryBookInfoVO = new LibraryBookInfoVO();
        if(bookImg.getOriginalFilename().equals("")){
            //만약 사진이 올라가지 않는다면 아래 코드 실행
            System.out.println("사진 안올렸음;");
            //그냥 디폴트 사진 설정
            libraryBookInfoVO.setBookInfoOriginFileName("book_character_smile.png");
            //그냥 디폴트 사진2 설정
            libraryBookInfoVO.setBookInfoAttachedFileName("book_character_smile.png");
            //책 소개 넣기
            libraryBookInfoVO.setBookIntro(bookIntro);
            //카테고리 정보도 넣기
            libraryBookInfoVO.setBookCateCode(bookCateCode);
            //만든 객체에 커맨더 객체(libraryBookInfoVO)에 있는 중분류 카테고리 정보 넣기
            libraryBookInfoVO.setBookMidCateCode(bookMidCateCode);

            //bookcode 넣어주기
            libraryBookInfoVO.setBookCode(libraryBookService.searchMaxCode());

            //bookvo에 infovo 넣기
            libraryBookVO.setLibraryBookInfoVO(libraryBookInfoVO);
        }else{
            //사진이 올라왔다면
            //uploadutil에 있는 uploadFile(리턴값 bookinfo)를 libraryBookInfoVO1 에 넣음
            LibraryBookInfoVO libraryBookInfoVO1 = UploadUtil.uploadFile(bookImg);
            //libraryBookInfoVO1에 책 소개 넣기
            libraryBookInfoVO1.setBookIntro(bookIntro);
            //카테고리 정보 넣기
            libraryBookInfoVO1.setBookCateCode(bookCateCode);
            //만든 객체에 커맨더 객체(libraryBookInfoVO)에 있는 중분류 카테고리 정보 넣기
            libraryBookInfoVO1.setBookMidCateCode(bookMidCateCode);

            libraryBookInfoVO1.setBookCode(libraryBookService.searchMaxCode());
            //bookvo에 infovo 넣기
            libraryBookVO.setLibraryBookInfoVO(libraryBookInfoVO1);
        }
        //bookvo에도 bookcode 넣어주기
        libraryBookVO.setBookCode(libraryBookService.searchMaxCode());
        System.out.println(libraryBookVO);

        buyService.regBook(libraryBookVO);

        return "redirect:/bookAdmin/buyBook?insert=1";
    }

    //안할거임 ㅅㄱ
    @GetMapping("/donatedBook")
    public String goDonatedBook(Model model){
        //인터셉터로 넘겨줄 page
        model.addAttribute("page", "donatedBook");

        System.out.println("기증 자료 이동");
        return "content/library/buy/donatedBook";
    }
}
