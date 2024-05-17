package com.green.Library.library.buy.controller;

import com.green.Library.library.buy.service.BuyService;
import com.green.Library.libraryBook.vo.LibraryBookVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class BuyControllerTest {
    @Resource(name="buyService")
    BuyService buyService;

    @Test
    void bookRedisplay() {
        String bookCode = "GR0000000020";
        LibraryBookVO libraryBookVO = buyService.searchBookBreakageDetail2(bookCode);
        buyService.regBook(libraryBookVO);
        System.out.println("책 다시 등록");

        buyService.deleteBreakageBook(bookCode);
        buyService.deleteBreakageBook2(bookCode);
        System.out.println("책 다시 되돌리기");





    }
}