package com.green.Library.serviceTest;

import com.green.Library.web.findBook.service.FindBookService;
import com.green.Library.web.findBook.service.FindBookServiceImpl;
import com.green.Library.web.findBook.vo.FindBookVO;
import com.green.Library.web.findBook.vo.SearchDateVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class FindBookServiceImplTest {
    @Resource(name="findBookService")
    FindBookService findBookService;
//    @Test
//    public void 책정보번호찾은다음날짜바꾸기 (){
//        List<Integer> testList = findBookService.changeRegDate2();
//        testList.forEach(s-> findBookService.changeRegDate(s));
//
//    }
    @Test
    public void 새로들어온책리스트_확인(){
        SearchDateVO searchDateVO = new SearchDateVO();
//        Optional<List<FindBookVO>> findBookVOList = findBookService.recommendedBookList("asdasd");
        FindBookVO f1 = findBookService.findBookOne("asvx");
        Optional<FindBookVO> o1 = Optional.ofNullable(f1);


//        System.out.println(findBookVOList.get());
    }


}
