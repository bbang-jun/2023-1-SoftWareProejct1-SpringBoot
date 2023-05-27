package com.example.Proj1_2019202023.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PictureBoardController {

    // 다중 매핑으로 localhost:8080과 localhost:8080/index.html 모두 접근 가능하도록 처리
    @RequestMapping(value={"/", "/index.html"}, method = RequestMethod.GET)
    public String home(){
        return "Index";
    }
}
