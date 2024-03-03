package com.spring.Library;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @GetMapping("/")
    public String ulsanLibrary(){
        return "redirect:ulsanlibrary/index";
    }
}
