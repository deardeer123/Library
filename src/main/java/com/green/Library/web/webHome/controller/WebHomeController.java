package com.green.Library.web.webHome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebHomeController {


    @GetMapping("/home")
    public String goHome(){
        System.out.println("홈");
        return "content/homePage/home";
    }





}
