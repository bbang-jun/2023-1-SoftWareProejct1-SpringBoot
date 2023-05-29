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


import java.io.File;
import java.util.List;

@Controller
public class PictureBoardController {

    private final PictureBoardService pictureBoardService;

    @Autowired
    public PictureBoardController(PictureBoardService pictureBoardService){
        this.pictureBoardService = pictureBoardService;
    }

    // 다중 매핑으로 localhost:8080과 localhost:8080/index.html 모두 접근 가능하도록 처리
    @RequestMapping(value={"/", "/index.html"}, method = RequestMethod.GET)
    public String home(Model model){
        List<PictureBoardDto> pictureBoardDtoList = pictureBoardService.findAll();
        model.addAttribute("pictureBoards", pictureBoardDtoList);
        return "Index";
    }

    // ImageView.html
    // 제목이나 이미지 눌러서 확인하는 정보 시각화
    @GetMapping("/imageview/{id}")
    public String moveImageViewPage(@PathVariable Long id, Model model){
        PictureBoardDto pictureBoardDto = pictureBoardService.findById(id);
        model.addAttribute("board", pictureBoardDto);
        return "ImageView";
    }

    // ImageView.html (delete API)
    // "삭제" 버튼 클릭 시 제거 후 Index.html로 이동
    @GetMapping("/imageview/delete/{id}")
    public String clickDelete(@PathVariable Long id){
        pictureBoardService.deleteById(id);
        return "redirect:/index.html";
    }

    // Index.html
    // "사진 올리기" 버튼 클릭 시 Upload.html로 이동
    // ImageView.html
    // "수정" 버튼 클릭 시 Upload.html로 이동
    @RequestMapping(value = {"/upload.html", "/upload.html/{id}"}, method = RequestMethod.GET)
    public String moveUploadPage(@PathVariable(required = false)Long id, Model model) {
        if(id != null) {
            PictureBoardDto pictureBoardDto = pictureBoardService.findById(id);
            model.addAttribute("modify", pictureBoardDto);
        }
        return "Upload";
    }

    // Upload.html
    // "전송" 버튼 클릭 시 Index.html
    // Upload.html
    // 수정 완료
    @RequestMapping(value = {"/upload.html", "/upload.html/{id}"}, method = RequestMethod.POST)
    public String handleFormSubmission(@PathVariable(required = false) Long id,
                                       @ModelAttribute PictureBoardDto pictureBoardDto,
                                       @RequestParam("file")MultipartFile file) throws Exception {
        if (id != null) {
            PictureBoardDto findBoardDto = pictureBoardService.findById(pictureBoardDto.getId());
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\" + findBoardDto.getFileName();
            File deleteFile = new File(filePath);
            deleteFile.delete();
            pictureBoardService.update(pictureBoardDto, file);
        }
        else {
            pictureBoardService.create(pictureBoardDto, file);
        }

        return "redirect:/index.html";
    }

    @ResponseBody
    @GetMapping(path = "/static/{fileName}")
    public Resource returnImage(@PathVariable String fileName) throws Exception{
        String filePath = System.getProperty("user.dir") + "/src/main/resources/static/" + fileName;
        return new UrlResource("file:"+ filePath);
    }
}