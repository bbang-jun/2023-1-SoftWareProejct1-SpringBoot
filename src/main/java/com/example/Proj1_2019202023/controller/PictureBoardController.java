package com.example.Proj1_2019202023.controller;

import com.example.Proj1_2019202023.dto.PictureBoardDto;
import com.example.Proj1_2019202023.service.PictureBoardService;
import org.apache.coyote.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class PictureBoardController {

    private final PictureBoardService pictureBoardService;

    public PictureBoardController(PictureBoardService pictureBoardService){
        this.pictureBoardService = pictureBoardService;
    }

    // 다중 매핑으로 localhost:8080과 localhost:8080/index.html 모두 접근 가능하도록 처리
    @RequestMapping(value={"/", "/index.html"}, method = RequestMethod.GET)
    public String home(Model model){
        List<PictureBoardDto> pictureBoardDtoList = pictureBoardService.findAll();
        model.addAttribute("boardList", pictureBoardDtoList);
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
    public String handleFormSubmission(@PathVariable(required = false) Long id, @ModelAttribute PictureBoardDto pictureBoardDto) {
        if (id != null) {
            pictureBoardService.update(pictureBoardDto);
        }
        else {
            pictureBoardService.create(pictureBoardDto);
        }

        return "redirect:/index.html";
    }
}

// Upload.html
// "전송" 버튼 클릭 시 Index.html
//@PostMapping("/upload.html")
// String createAPI(@ModelAttribute PictureBoardDto pictureBoardDto){
//    pictureBoardService.create(pictureBoardDto);
//    return "redirect:/index.html";
//}

// Upload.html
// 수정 완료
//@PostMapping("/upload.html/{id}")
//public String clickModifyUpload(@ModelAttribute PictureBoardDto pictureBoardDto){
//    pictureBoardService.update(pictureBoardDto);
//    return "redirect:/index.html";
//}

// Index.html
// "사진 올리기" 버튼 클릭 시 Upload.html로 이동
//@GetMapping("/upload.html")
//public String clickPictureUpload(){
//    return "Upload";
//}

// ImageView.html
// "수정" 버튼 클릭 시 Upload.html로 이동
//@GetMapping("/upload.html/{id}")
//public String clickModify(@PathVariable Long id, Model model){
//    PictureBoardDto pictureBoardDto = pictureBoardService.findById(id);
//    model.addAttribute("modify", pictureBoardDto);
//    return "Upload";
//}