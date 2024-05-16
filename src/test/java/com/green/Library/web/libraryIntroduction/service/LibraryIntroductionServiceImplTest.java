package com.green.Library.web.libraryIntroduction.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryIntroductionServiceImplTest {
    @Resource(name="libraryIntroductionService")
    private LibraryIntroductionService libraryIntroductionService;

    @Test
    public void 도서관현황테스트(){
        List<Map<String, Object>> map = libraryIntroductionService.libraryState();
        map.forEach(s-> System.out.println(s));
        Integer count = libraryIntroductionService.libraryState2();
        Map<String, Object> map2 = new HashMap<>();
        map2.put("state", map);
        map2.put("count", count);
        System.out.println(map2);

    }

}