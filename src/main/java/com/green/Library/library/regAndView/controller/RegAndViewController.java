package com.green.Library.library.regAndView.controller;

import com.green.Library.library.libraryMenu.service.LibraryMenuService;
import com.green.Library.library.regAndView.service.RegAndViewService;
import com.green.Library.libraryBook.service.LibraryBookService;
import com.green.Library.libraryBook.vo.LibraryBookInfoVO;
import com.green.Library.libraryBook.vo.LibraryBookVO;
import com.green.Library.util.UploadUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    public String goWorkingBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());

        //대충 책 정보 가져 오고나서 해당 파일의 상세 정보등을 변경시키게
        System.out.println(regAndViewService.selectBookList());
        model.addAttribute("bookList", regAndViewService.selectBookList());

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
        model.addAttribute("cateList",libraryBookService.selectCateList());
        
        //확인좀
        System.out.println(regAndViewService.selectOneBook(bookCode));

        model.addAttribute("book",regAndViewService.selectOneBook(bookCode));
        return "content/library/regAndView/changeBook";
    }
    @PostMapping("/changeBook2")
    public String changeBook(LibraryBookVO libraryBookVO,
                             LibraryBookInfoVO libraryBookInfoVO,
                             @RequestParam(name="bookImg") MultipartFile bookImg){
        System.out.println("책 내용바꾸기!!");

        System.out.println(libraryBookVO);
        System.out.println(libraryBookInfoVO);
        System.out.println(bookImg.getOriginalFilename());
        //아니 근데 이미지 변경하면서 원래 있던 이미지를 삭제해야하는데 라;ㅣㄴㅇㅁㄹㄴㅇ;ㅏㅣㅁㅇㄹ나;ㅣㅁㅇㄹ나;ㅣ

        //일단 손이 가는대로 적음

//        if(bookImg.getOriginalFilename().equals("")){
//            //만약 사진이 올라가지 않는다면 아래 코드 실행
//            System.out.println("사진 안올렸음;");
//            //그냥 디폴트 사진 설정
//            libraryBookInfoVO.setBookInfoOriginFileName("book_character_smile.png");
//            //그냥 디폴트 사진2 설정
//            libraryBookInfoVO.setBookInfoAttachedFileName("book_character_smile.png");
//            //책 소개 넣기
//            libraryBookInfoVO.setBookIntro(bookIntro);
//            //카테고리 정보도 넣기
//            libraryBookInfoVO.setBookCateCode(bookCateCode);
//            //bookcode 넣어주기
//            libraryBookInfoVO.setBookCode(libraryBookService.searchMaxCode());
//
//            //bookvo에 infovo 넣기
//            libraryBookVO.setLibraryBookInfoVO(libraryBookInfoVO);
//        }else{
//            //사진이 올라왔다면
//            //uploadutil에 있는 uploadFile(리턴값 bookinfo)를 libraryBookInfoVO1 에 넣음
//            LibraryBookInfoVO libraryBookInfoVO1 = UploadUtil.uploadFile(bookImg);
//            //libraryBookInfoVO1에 책 소개 넣기
//            libraryBookInfoVO1.setBookIntro(bookIntro);
//            //카테고리 정보 넣기
//            libraryBookInfoVO1.setBookCateCode(bookCateCode);
//            libraryBookInfoVO1.setBookCode(libraryBookService.searchMaxCode());
//            //bookvo에 infovo 넣기
//            libraryBookVO.setLibraryBookInfoVO(libraryBookInfoVO1);
//        }
//        //bookvo에도 bookcode 넣어주기
//        libraryBookVO.setBookCode(libraryBookService.searchMaxCode());
//        System.out.println(libraryBookVO);
//
//        buyService.regBook(libraryBookVO);

        return "redirect:/bookAdmin/workingBook";
    }

    @GetMapping("/collectionBook")
    public String goCollectionBook(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("소장 자료 관리 이동");
        return "content/library/regAndView/collectionBook";
    }
    @GetMapping("/markImport")
    public String goMarkImport(Model model){
        //이동하기전 메뉴리스트 가져가기
        model.addAttribute("menuList", libraryMenuService.selectLibraryMenuList());


        System.out.println("마크 반입 이동");
        return "content/library/regAndView/markImport";
    }
}
