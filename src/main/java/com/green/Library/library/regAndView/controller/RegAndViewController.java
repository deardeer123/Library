package com.green.Library.library.regAndView.controller;

import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.library.regAndView.service.RegAndViewService;
import com.green.Library.libraryBook.service.LibraryBookService;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import com.green.Library.libraryBook.vo.LibraryBookInfoVO;
import com.green.Library.libraryBook.vo.LibraryBookMidCategoryVO;
import com.green.Library.libraryBook.vo.LibraryBookVO;
import com.green.Library.util.UploadUtil;
import jakarta.annotation.Resource;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bookAdmin")
public class RegAndViewController {
    @Resource(name="menuService")
    LibraryMenuService libraryMenuService;

    @Resource(name="libraryBookService")
    LibraryBookService libraryBookService;

    @Resource(name="regAndViewService")
    RegAndViewService regAndViewService;

    //----------------등록 열람----------------
    @GetMapping("/workingBook")
    public String goWorkingBook(Model model,
                                BookSearchVO bookSearchVO,
                                @RequestParam(name="update" ,required = false, defaultValue = "0") int update ,
                                @RequestParam(name="delete", required = false, defaultValue = "0") int delete){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());

        System.out.println(bookSearchVO);

        System.out.println("------------------------"+ bookSearchVO.getNowPage());
        //페이징
        System.out.println(bookSearchVO.getNowPage());
        bookSearchVO.setNowPage(bookSearchVO.getNowPage());

        //전체 게시물 갯수 설정
        int totalDataCnt = regAndViewService.selectBookCnt(bookSearchVO);
        bookSearchVO.setTotalDataCnt(totalDataCnt);

        //페이지 정보 세팅
        bookSearchVO.setPageInfo();
        // 끝나는 페이지, 마지막 페이지 확인
        System.out.println("endPage : " + bookSearchVO.getEndPage());
        System.out.println("totalPage : " + bookSearchVO.getTotalPageCnt());
        System.out.println("prev : " + bookSearchVO.getPrev());
        System.out.println("next : " + bookSearchVO.getNext());


//
        //대충 책 정보 가져 오고나서 해당 파일의 상세 정보등을 변경시키게
        System.out.println(regAndViewService.searchBookList(bookSearchVO));
        model.addAttribute("bookList", regAndViewService.searchBookList(bookSearchVO));

        //변경 혹은 삭제 하고나서 해당 페이지로 이동 시 알람창 하나 띄우고 싶어서 적은 코드
        System.out.println(update);
        model.addAttribute("update", update);

        System.out.println(delete);
        model.addAttribute("delete", delete);


        
        
        







        //초기화
//        List<Integer> initList = new ArrayList<>();
//        for(int i=1; i<39;i++){
//            initList.add(i);
//        }
//        System.out.println(initList);
//        이거 bookinfo 초기화 코드입니다 건드지 마십쇼
//        regAndViewService.bookInfoInit(initList);

        System.out.println("작업 자료 관리 이동");
        return "content/library/regAndView/workingBook";
    }

    //일단 책 정보 변경하는거 간단하게 가야할듯..
    @GetMapping("/changeBook")
    public String goChange(Model model, @RequestParam(name="bookCode")String bookCode){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());

        //가기전에 카테고리 목록 리스트 가져오기
        //System.out.println(libraryBookService.selectCateList());
        List<LibraryBookCategoryVO> cateList = libraryBookService.selectCateList();
        model.addAttribute("cateList",cateList);
        System.out.println(cateList);
        
        //확인좀
        System.out.println(regAndViewService.selectOneBook(bookCode));

        model.addAttribute("book",regAndViewService.selectOneBook(bookCode));
        return "content/library/regAndView/changeBook";
    }
    @ResponseBody
    @PostMapping("/selectCateList")
    public LibraryBookCategoryVO giveMeCateList(@RequestParam(name="bookCateCode")int bookCateCode){
        System.out.println("중분류 카테고리 넘겨주기");
        LibraryBookCategoryVO libraryBookCategoryVO = libraryBookService.selectCateOne(bookCateCode);
        return libraryBookCategoryVO;
    }

    @PostMapping("/changeBook2")
    public String changeBook(LibraryBookVO libraryBookVO,
                             LibraryBookInfoVO libraryBookInfoVO,
                             @RequestParam(name="bookImg") MultipartFile bookImg){
        System.out.println("책 내용바꾸기!!");

        System.out.println(libraryBookVO);
        System.out.println(libraryBookInfoVO);
        System.out.println(bookImg.getOriginalFilename());
        //아니 근데 이미지 변경하면서 원래 있던 이미지를 삭제해야하는데 맞는거 같은데


        if(bookImg.getOriginalFilename().equals("")){
            LibraryBookInfoVO libraryBookInfoVO1 = new LibraryBookInfoVO();
            //만약 사진이 올라가지 않는다면 아래 코드 실행
            System.out.println("사진 안올렸음;");
            //사진을 올리지 않았다면 원래 올려진 사진을 그대로 사용
            libraryBookInfoVO1.setBookInfoOriginFileName(libraryBookInfoVO.getBookInfoOriginFileName());
            libraryBookInfoVO1.setBookInfoAttachedFileName(libraryBookInfoVO.getBookInfoAttachedFileName());

            //만든 객체에 커맨더 객체(libraryBookInfoVO)로 받은 책 소개 넣기
            libraryBookInfoVO1.setBookIntro(libraryBookInfoVO.getBookIntro());
            ///만든 객체에 커맨더 객체(libraryBookInfoVO)로 카테고리 정보도 넣기
            libraryBookInfoVO1.setBookCateCode(libraryBookInfoVO.getBookCateCode());
            ///만든 객체에 커맨더 객체(libraryBookInfoVO)로 중분류 카테고리 정보도 넣기
            libraryBookInfoVO1.setBookMidCateCode(libraryBookInfoVO.getBookMidCateCode());

            ///만든 객체에 커맨더 객체(libraryBookInfoVO)로 bookcode 넣어주기
            libraryBookInfoVO1.setBookCode(libraryBookInfoVO.getBookCode());

            //보낼 bookvo에 새루운 객체 넣기
            libraryBookVO.setLibraryBookInfoVO(libraryBookInfoVO1);
        }else{
            //사진이 올라왔다면
            //uploadutil에 있는 uploadFile(리턴값 librarybookinfo)를 libraryBookInfoVO1 에 넣음
            LibraryBookInfoVO libraryBookInfoVO1 = UploadUtil.uploadFile(bookImg);
            ///만든 객체에 커맨더 객체(libraryBookInfoVO)에 있는 책 소개 넣기
            libraryBookInfoVO1.setBookIntro(libraryBookInfoVO.getBookIntro());
            ///만든 객체에 커맨더 객체(libraryBookInfoVO)에 있는 카테고리 정보 넣기
            libraryBookInfoVO1.setBookCateCode(libraryBookInfoVO.getBookCateCode());
            //만든 객체에 커맨더 객체(libraryBookInfoVO)에 있는 중분류 카테고리 정보 넣기
            libraryBookInfoVO1.setBookMidCateCode(libraryBookInfoVO.getBookMidCateCode());
            ///만든 객체에 커맨더 객체(libraryBookInfoVO)에 있는 책코드 넣기
            libraryBookInfoVO1.setBookCode(libraryBookInfoVO.getBookCode());
            //bookvo에 infovo 넣기
            libraryBookVO.setLibraryBookInfoVO(libraryBookInfoVO1);
        }
        //데이터가 제대로 들어갔는지 확인좀
        System.out.println(libraryBookVO);

        regAndViewService.updateBook(libraryBookVO);

        //변경 되었으면 변경됬다고 뭐하나 띄우고 싶은데
        return "redirect:/bookAdmin/workingBook?update=1";
    }

    //책 삭제하기
    @GetMapping("/deleteBook1")
    public String deleteBook1(@RequestParam(name="bookCode")String bookCode){
        System.out.println(bookCode);

//        받은 bookCode로 쿼리문 실행
        regAndViewService.deleteBookOne(bookCode);

        return "redirect:/bookAdmin/workingBook?delete=1";
    }

    @ResponseBody
    @PostMapping("/searchBook")
    public Map<String,Object> doSearchBook(BookSearchVO bookSearchVO){


        System.out.println("이동합니까?");
        System.out.println(bookSearchVO);
        //가져갈 책 리스트
        List<LibraryBookVO> libraryBookVOList = regAndViewService.searchBookList(bookSearchVO);

        //페이징
        System.out.println(bookSearchVO.getNowPage());


        //전체 게시물 갯수 설정
        int totalDataCnt = regAndViewService.selectBookCnt(bookSearchVO);
        bookSearchVO.setTotalDataCnt(totalDataCnt);

        //페이지 정보 세팅
        bookSearchVO.setPageInfo();
        // 끝나는 페이지, 마지막 페이지 확인
        System.out.println("endPage : " + bookSearchVO.getEndPage());
        System.out.println("totalPage : " + bookSearchVO.getTotalPageCnt());
        System.out.println("prev : " + bookSearchVO.getPrev());
        System.out.println("next : " + bookSearchVO.getNext());



        //bookSearchVO 내용도 던져줄려면 map 써야할듯
        Map<String,Object> bookInfo = new HashMap<String,Object>();
        bookInfo.put("libraryBookVOList",regAndViewService.searchBookList(bookSearchVO));
        bookInfo.put("bookSearchVO",bookSearchVO);


       return bookInfo;
    }


    @GetMapping("/collectionBook")
    public String goCollectionBook(Model model , BookSearchVO bookSearchVO){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());

        //페이징
        System.out.println(bookSearchVO.getNowPage());
        bookSearchVO.setNowPage(bookSearchVO.getNowPage());

        //전체 게시물 갯수 설정
        int totalDataCnt = regAndViewService.selectBookCnt(bookSearchVO);
        bookSearchVO.setTotalDataCnt(totalDataCnt);

        //페이지 정보 세팅
        bookSearchVO.setPageInfo();

        // 끝나는 페이지, 마지막 페이지 확인
        System.out.println("endPage : " + bookSearchVO.getEndPage());
        System.out.println("totalPage : " + bookSearchVO.getTotalPageCnt());
        System.out.println("prev : " + bookSearchVO.getPrev());
        System.out.println("next : " + bookSearchVO.getNext());


//
        //대충 책 정보 가져 오고나서 해당 파일의 상세 정보등을 변경시키게
        System.out.println(regAndViewService.searchBookList(bookSearchVO));
        model.addAttribute("bookList", regAndViewService.searchBookList(bookSearchVO));


        System.out.println("소장 자료 관리 이동");
        return "content/library/regAndView/collectionBook";
    }


    //이건
    @ResponseBody
    @PostMapping("/modal")
    public Map<String,Object> modal(@RequestParam(name="bookCode")String bookCode) {
        System.out.println(bookCode);
        Map<String,Object> bookInfo = new HashMap<>();
        bookInfo.put("book",regAndViewService.selectOneBook(bookCode));
        bookInfo.put("cateList",libraryBookService.selectCateList());

        return bookInfo;
    }

    @ResponseBody
    @PostMapping("/bookDetailInfo")
    public Map<String,Object> modal2(@RequestParam(name="bookCode")String bookCode) {
        System.out.println(bookCode);
        Map<String,Object> bookInfo = new HashMap<>();
        bookInfo.put("book",regAndViewService.selectOneBook(bookCode));
        bookInfo.put("cateList",libraryBookService.selectCateList());

        return bookInfo;
    }



    @GetMapping("/markImport")
    public String goMarkImport(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("마크 반입 이동");
        return "content/library/regAndView/markImport";
    }
}
