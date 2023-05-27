package com.example.Proj1_2019202023.controller;

import com.example.Proj1_2019202023.service.PictureBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PictureBoardController {

    private final PictureBoardService pictureBoardService;

    public PictureBoardController(PictureBoardService pictureBoardService){
        this.pictureBoardService = pictureBoardService;
    }

    // 다중 매핑으로 localhost:8080과 localhost:8080/index.html 모두 접근 가능하도록 처리
    @RequestMapping(value={"/", "/index.html"}, method = RequestMethod.GET)
    public String home(){
        return "Index";
    }

    // Index.html
    // "사진 올리기" 버튼 클릭 시 Upload.html로 이동
    @GetMapping("/upload.html")
    public String clickPictureUpload(){
        return "Upload";
    }

    // Upload.html
    // "전송" 버튼 클릭 시 Index.html
    @PostMapping("/upload.html")
    public String createAPI(){
        return "redirect:/index.html";
    }
}
