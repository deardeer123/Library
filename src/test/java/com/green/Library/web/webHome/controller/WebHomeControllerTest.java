package com.green.Library.web.webHome.controller;

import com.green.Library.web.webHome.service.WebHomeService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebHomeControllerTest {
    @Resource(name="webHomeService")
    private WebHomeService webHomeService;
    @Test
    public void 왜안되니(){
        List<Map<String,Object>> mapList = webHomeService.libraryEventList3();
        for(Map<String,Object> e : mapList){
            System.out.println(e);
            e.put("asdf",1234);
            System.out.println(e);
        }
    }
}