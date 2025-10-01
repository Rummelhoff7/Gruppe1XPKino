package org.example.gruppe1xpkino.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KinoController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/sales_page")
    public String SalesPage(){
        return "sales_page";
    }

}
