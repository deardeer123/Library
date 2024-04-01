package com.green.Library.library.regAndView.controller;

import com.green.Library.library.borrowReturn.vo.BookBNRVO;
import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.library.regAndView.service.BookSearchVO;
import com.green.Library.library.regAndView.service.RegAndViewService;
import com.green.Library.libraryBook.service.LibraryBookService;
import com.green.Library.libraryBook.vo.LibraryBookCategoryVO;
import com.green.Library.libraryBook.vo.LibraryBookInfoVO;
import com.green.Library.libraryBook.vo.LibraryBookMidCategoryVO;
import com.green.Library.libraryBook.vo.LibraryBookVO;
import com.green.Library.util.UploadUtil;
import com.green.Library.web.member.vo.MemberVO;
import com.green.Library.web.webMenu.service.WebMenuService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.One;
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

    @Resource(name="webMenuService")
    WebMenuService webMenuService;

    //----------------등록 열람----------------
    @GetMapping("/workingBook")
    public String goWorkingBook(Model model,
                                BookSearchVO bookSearchVO,
                                @RequestParam(name="update" ,required = false, defaultValue = "0") int update ,
                                @RequestParam(name="delete", required = false, defaultValue = "0") int delete){

        //인터셉터로 넘겨줄 page
        model.addAttribute("page", "workingBook");

        //전체 게시물 갯수 설정
        int totalDataCnt = regAndViewService.selectBookCnt(bookSearchVO);
        bookSearchVO.setTotalDataCnt(totalDataCnt);

        //페이지 정보 세팅
        bookSearchVO.setPageInfo();

        //대충 책 정보 가져 오고나서 해당 파일의 상세 정보등을 변경시키게
//        System.out.println(regAndViewService.searchBookList(bookSearchVO));
        model.addAttribute("bookList", regAndViewService.searchBookList(bookSearchVO));

        //변경 혹은 삭제 하고나서 해당 페이지로 이동 시 알람창 하나 띄우고 싶어서 적은 코드
        model.addAttribute("update", update);
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
        //인터셉터로 넘겨줄 page
        model.addAttribute("page", "workingBook");

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
    @PostMapping("/selectCateListFetch")
    public LibraryBookCategoryVO giveMeCateList(@RequestParam(name="bookCateCode")int bookCateCode){
        System.out.println("중분류 카테고리 넘겨주기");
        LibraryBookCategoryVO libraryBookCategoryVO = libraryBookService.selectCateOne(bookCateCode);
        return libraryBookCategoryVO;
    }

    @PostMapping("/changeBook2")
    public String changeBook(LibraryBookVO libraryBookVO,
                             LibraryBookInfoVO libraryBookInfoVO,
                             @RequestParam(name="bookImg") MultipartFile bookImg){


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
        //받은 북 코드를 보내서 book_breakage 테이블로 보낼거임
        LibraryBookVO libraryBookVO = regAndViewService.selectOneBook(bookCode);

        regAndViewService.insertBookBreakageOne(libraryBookVO);
        regAndViewService.deleteBookOne(bookCode);



        //받은 bookCode로 쿼리문 실행
        //regAndViewService.deleteBookOne(bookCode);

        return "redirect:/bookAdmin/workingBook?delete=1";
    }

    @ResponseBody
    @PostMapping("/searchBookFetch")
    public Map<String,Object> doSearchBook(BookSearchVO bookSearchVO){
        //가져갈 책 리스트
        List<LibraryBookVO> libraryBookVOList = regAndViewService.searchBookList(bookSearchVO);

        //페이징
        System.out.println(bookSearchVO.getNowPage());

        //전체 게시물 갯수 설정
        int totalDataCnt = regAndViewService.selectBookCnt(bookSearchVO);
        bookSearchVO.setTotalDataCnt(totalDataCnt);

        //페이지 정보 세팅
        bookSearchVO.setPageInfo();

        //bookSearchVO 내용도 던져줄려면 map 써야할듯
        Map<String,Object> bookInfo = new HashMap<String,Object>();
        bookInfo.put("libraryBookVOList",regAndViewService.searchBookList(bookSearchVO));
        bookInfo.put("bookSearchVO",bookSearchVO);

       return bookInfo;
    }


    @GetMapping("/collectionBook")
    public String goCollectionBook(Model model , BookSearchVO bookSearchVO){
        //인터셉터로 넘겨줄 page
        model.addAttribute("page", "collectionBook");

        //페이징
        System.out.println(bookSearchVO.getNowPage());
        bookSearchVO.setNowPage(bookSearchVO.getNowPage());

        //전체 게시물 갯수 설정
        int totalDataCnt = regAndViewService.selectBookCnt(bookSearchVO);
        bookSearchVO.setTotalDataCnt(totalDataCnt);

        //페이지 정보 세팅
        bookSearchVO.setPageInfo();

        //대충 책 정보 가져 오고나서 해당 파일의 상세 정보등을 변경시키게
        System.out.println(regAndViewService.searchBookList(bookSearchVO));
        model.addAttribute("bookList", regAndViewService.searchBookList(bookSearchVO));

        System.out.println("소장 자료 관리 이동");
        return "content/library/regAndView/collectionBook";
    }

    @ResponseBody
    @PostMapping("/bookDetailInfoFetch")
    public Map<String,Object> bookInfo(@RequestParam(name="bookCode")String bookCode) {

        Map<String, Object> bookInfo = new HashMap<>();
        MemberVO memberVO = regAndViewService.bookDetailInfo(bookCode);
        
        LibraryBookCategoryVO libraryBookCategoryVO = regAndViewService.selectCateNameOne(bookCode);
        bookInfo.put("memberVO",memberVO);
        bookInfo.put("libraryBookCategoryVO",libraryBookCategoryVO);
        return bookInfo;
        
    }
//내가 짜놓고 뭔지 모르겠네
    @ResponseBody
    @PostMapping("/findBookWithCodeFetch")
    public Map<String, Object> findBookWithCode(BookSearchVO bookSearchVO){
        //책정보랑 그책에 관련된 회원들도 보여줘야함
        Map<String , Object> bookInfo = new HashMap<>();
        String bookCode = bookSearchVO.getSearchValue();
        MemberVO memberVO = regAndViewService.bookDetailInfo(bookCode);

        LibraryBookCategoryVO libraryBookCategoryVO = regAndViewService.selectCateNameOne(bookCode);
        bookInfo.put("memberVO",memberVO);
        bookInfo.put("libraryBookCategoryVO",libraryBookCategoryVO);

        return bookInfo;
    }

    @GetMapping("/markImport")
    public String goMarkImport(Model model){
        //인터셉터로 넘겨줄 page
        model.addAttribute("page", "markImport");

        //바코드 출력을 위한 책 넘겨주기
        List<LibraryBookVO> bookList = regAndViewService.selectBookList2();
        model.addAttribute("bookList",bookList);

        return "content/library/regAndView/markImport";
    }

    @PostMapping("/printBarCode")
    public String printBarCode(@RequestParam(name="bookCodeList")List<String> bookCodeList,Model model){
        System.out.println(bookCodeList);
        List<LibraryBookVO> bookList = regAndViewService.bookBarCodeList(bookCodeList);
        model.addAttribute("bookList", bookList);

        return "content/library/regAndView/bar_code_print";
    }

    //    //이건
//    @ResponseBody
//    @PostMapping("/modal")
//    public Map<String,Object> modal(@RequestParam(name="bookCode")String bookCode) {
//
//        Map<String,Object> bookInfo = new HashMap<>();
//        bookInfo.put("book",regAndViewService.selectOneBook(bookCode));
//        bookInfo.put("cateList",libraryBookService.selectCateList());
//
//        return bookInfo;
//    }
}
