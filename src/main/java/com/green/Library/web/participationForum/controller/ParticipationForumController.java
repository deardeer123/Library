package com.green.Library.web.participationForum.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forum")
public class ParticipationForumController {


    @GetMapping("/Notice")
    public String Notice (){
        return "content/homePage/participationForum/Notice";
    }

    @GetMapping("/NoticeWriter")
    public String NoticeWriter (){
        return "content/homePage/participationForum/NoticeWriter";
    }

}
