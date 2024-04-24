package com.green.Library.serviceTest;

import com.green.Library.libraryBook.service.LibraryBookService;
import com.green.Library.libraryBook.vo.BookRecommendationVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
}
