package com.green.Library.serviceTest;

import com.green.Library.libraryBook.service.LibraryBookService;
import com.green.Library.libraryBook.vo.BookRecommendationVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@Transactional
public class LibraryBookServiceImplTest {
    @Resource(name = "libraryBookService")
    LibraryBookService libraryBookService;

    @Test
    public void 추천책_테스트 (){
        BookRecommendationVO bookRecommendationVO = new BookRecommendationVO().builder()
                .bookCode("GR0000000024")

                .userType("adult")
                .build();

        BookRecommendationVO bookRecommendationVO2 = new BookRecommendationVO().builder()
                .bookCode("GR0000000001")

                .userType("adult")
                .build();
        System.out.println("첫번째 -> 추천책 데이터가 없는 경우 삽입");
        libraryBookService.insertBookRecommendation(bookRecommendationVO);
        System.out.println("두번째 -> 추천책 데이터가 있는 경우 변경");
        libraryBookService.insertBookRecommendation(bookRecommendationVO2);
        System.out.println();
    }
    @Test
    public void 오늘날짜(){
        LocalDate date = LocalDate.now(); //오늘 날짜 LocalDate 객체 생성
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = date.format(dateTimeFormatter); //LocalDate 객체를 String 객체로 바꿈
        System.out.println(today);
    }
}
