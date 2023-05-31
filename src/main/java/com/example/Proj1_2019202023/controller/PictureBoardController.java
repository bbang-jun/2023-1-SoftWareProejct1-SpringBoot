package com.example.Proj1_2019202023.controller;

import com.example.Proj1_2019202023.dto.PictureBoardDto;
import com.example.Proj1_2019202023.service.PictureBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller // controller로써의 spring bean으로 등록
public class PictureBoardController {

    private final PictureBoardService pictureBoardService; // service class에 대한 객체

    @Autowired // 의존성 주입(생성자 주입 방식)
    public PictureBoardController(PictureBoardService pictureBoardService){
        this.pictureBoardService = pictureBoardService; // 지역으로 선언한 service에 대해 의존성 주입
    }

    // 다중 매핑으로 localhost:8080과 localhost:8080/index.html 모두 접근 가능하도록 처리
    @RequestMapping(value={"/", "/index.html"}, method = RequestMethod.GET)
    public String home(Model model){
        List<PictureBoardDto> pictureBoardDtoList = pictureBoardService.findAll(); // 현재까지 저장된 모든 게시물 정보를 List 형태로 가져옴
        model.addAttribute("pictureBoards", pictureBoardDtoList); // thymeleaf에서의 변수로 사용할 수 있도록 model 객체에 담아서 모든 게시물을 보여줌
        return "Index"; // Index.html을 띄움
    }

    // ImageView.html
    // 제목이나 이미지 눌러서 확인하는 정보 시각화
    @GetMapping("/imageview/{id}")
    public String moveImageViewPage(@PathVariable Long id, Model model){
        PictureBoardDto pictureBoardDto = pictureBoardService.findById(id);
        model.addAttribute("board", pictureBoardDto); // 제목, 내용, 이미지를 확인할 수 있도록 해당 게시글에 대한 정보를 model 객체에 담아서 thymleaf 문법으로 사용
        return "ImageView"; // 각 게시물에 대한 ImageView.html을 띄움
    }

    // ImageView.html (delete API)
    // "삭제" 버튼 클릭 시 제거 후 Index.html로 이동
    @GetMapping("/imageview/delete/{id}")
    public String clickDelete(@PathVariable Long id){
        pictureBoardService.deleteById(id); // id에 해당하는 게시물을 DB에서 제거함.
        return "redirect:/index.html"; // index.html로 리다이렉트를 통해 오류 방지
    }

    // Index.html에서는 "사진 올리기" 버튼 클릭 시 Upload.html로 이동하는 용도
    // ImageView.html에서는 "수정" 버튼 클릭 시 Upload.html로 이동하는 용도
    @RequestMapping(value = {"/upload.html", "/upload.html/{id}"}, method = RequestMethod.GET)
    public String moveUploadPage(@PathVariable(required = false)Long id, Model model) {
        if(id != null) {
            PictureBoardDto pictureBoardDto = pictureBoardService.findById(id); // 수정할 게시물인 경우 db로부터 entity -> dto로 변환하여 model 객체에 담음
            model.addAttribute("modify", pictureBoardDto); // Upload.html에서 thymleaf 문법으로 modify 변수 사용
        }
        return "Upload";
    }

    // Upload.html
    // "전송" 버튼 클릭 시 post 정보를 DB에 저장하고 Index.html로 이동(새로 만들 때, 수정할 때)
    @RequestMapping(value = {"/upload.html", "/upload.html/{id}"}, method = RequestMethod.POST)
    public String savePost(@PathVariable(required = false) Long id,
                           @ModelAttribute PictureBoardDto pictureBoardDto, // 제목, 내용, 사진을 입력한 후 전송 버튼을 누르면 pictureBoardDto에 자동으로 담김
                           @RequestParam("file")MultipartFile file) throws Exception {
        if (id != null) {
            pictureBoardService.update(pictureBoardDto, file); // 수정
        }
        else {
            pictureBoardService.create(pictureBoardDto, file); // 생성
        }
        return "redirect:/index.html";
    }

    // Upload.html
    // 이미지만 따로 재전송
    @ResponseBody
    @GetMapping(path = "/static/{fileName}")
    public Resource returnImage(@PathVariable String fileName) throws Exception{
        String filePath = System.getProperty("user.dir") + "/src/main/resources/static/" + fileName;
        return new UrlResource("file:"+ filePath); // URL resource만 따로 전송해줌으로써 이미지 미표시 해결
    }
}