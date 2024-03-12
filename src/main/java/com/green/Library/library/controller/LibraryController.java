package com.green.Library.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryController {

    @GetMapping("/")
    public String goHome(){
        System.out.println("홈");
        return "content/library/home";
    }
