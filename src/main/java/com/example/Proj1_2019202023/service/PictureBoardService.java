package com.example.Proj1_2019202023.service;

import com.example.Proj1_2019202023.dto.PictureBoardDto;
import com.example.Proj1_2019202023.entity.PictureBoardEntity;
import com.example.Proj1_2019202023.repository.PictureBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service // service로써의 sring bean으로 등록
public class PictureBoardService {

    private final PictureBoardRepository pictureBoardRepository; // repositroy에 대한 객체

    @Autowired // 의존성 주입(생성자 주입 방식)
    public PictureBoardService(PictureBoardRepository pictureBoardRepository){this.pictureBoardRepository = pictureBoardRepository;}

    @Transactional
    public void create(PictureBoardDto pictureBoardDto, MultipartFile file) throws Exception{ // db를 통해 db에서 게시물 정보를 저장하는 함수
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";
        UUID uuid = UUID.randomUUID(); // 저장한 게시물의 이름이 중복되면 오류가 발생하므로 UUID 생성
        String fileName = uuid + "_" + file.getOriginalFilename(); // 고유한 UUID와 파일이름을 연결하여 고유한 fileName 생성
        File saveFile = new File(filePath, fileName); // filePath와 고유한 fileName을 바탕으로 file 저장
        file.transferTo(saveFile);

        pictureBoardDto.setFileName(fileName); // dto에 file name 저장
        pictureBoardDto.setFilePath("/static/" + fileName); // dto에 file path 저장
        PictureBoardEntity pictureBoardEntity = PictureBoardEntity.toBoardEntity(pictureBoardDto); // dto -> entity로 변환
        pictureBoardRepository.save(pictureBoardEntity); // 변환한 entity를 h2 db에 저장
    }

    public void update(PictureBoardDto pictureBoardDto, MultipartFile file) throws Exception{ // 게시글 수정할 때의 함수
        PictureBoardDto findBoardDto = PictureBoardDto.toPictureBoardDto(pictureBoardRepository.findById(pictureBoardDto.getId()).get()); // 수정할 게시글의 정보를 db에서 가져옴
        String deleteFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\" + findBoardDto.getFileName(); // 게시글을 수정할 것이므로 기존의 사진의 제거를 위해 file path 저장
        File deleteFile = new File(deleteFilePath);
        deleteFile.delete(); // 기존의 사진 제거

        Optional<PictureBoardEntity> pictureBoardOptional = pictureBoardRepository.findById(pictureBoardDto.getId()); // 수정할 게시글
        if (pictureBoardOptional.isPresent()) { // 수정할 게시글이 존재하면
            PictureBoardEntity pictureBoardEntity = pictureBoardOptional.get(); // Optional형으로부터 .get() 통해 Entity만 추출

            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";
            UUID uuid = UUID.randomUUID(); // 저장한 게시물의 이름이 중복되면 오류가 발생하므로 UUID 생성
            String fileName = uuid + "_" + file.getOriginalFilename(); // 고유한 UUID와 파일이름을 연결하여 고유한 fileName 생성
            File saveFile = new File(filePath, fileName); // filePath와 고유한 fileName을 바탕으로 file 저장
            file.transferTo(saveFile);

            pictureBoardEntity.setTitle(pictureBoardDto.getTitle()); // entity에 title 저장
            pictureBoardEntity.setComment(pictureBoardDto.getComment()); // entity에 comment 저장
            pictureBoardEntity.setFileName(fileName); // entity에 file name 저장
            pictureBoardEntity.setFilePath("/static/" + fileName); // entity에 file path 저장

            pictureBoardRepository.save(pictureBoardEntity); // 수정한 게시글의 정보를 반영하여 db에 저장
        } else { // 수정할 게시글이 존재하지 않으면 예외처리
            throw new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + pictureBoardDto.getId());
        }
    }

    public List<PictureBoardDto> findAll(){ // db로부터 controller로 모든 게시물 정보를 보내주는 함수
        List<PictureBoardEntity> pictureBoardEntityList = pictureBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "id")); // 내림차순을 통해 최신순으로 게시글을 가져옴
        List<PictureBoardDto> pictureBoardDtoList = new ArrayList<>();
        for(PictureBoardEntity pictureBoardEntity : pictureBoardEntityList){ // db에 저장되어 있던 entity를 dto로 변환하여 List에 저장
            pictureBoardDtoList.add(PictureBoardDto.toPictureBoardDto(pictureBoardEntity));
        }
        return pictureBoardDtoList; // 모든 정보를 저장한 Dto List 반환
    }

    public PictureBoardDto findById(Long id){ // 게시글의 Id를 바탕으로 db에서 해당 게시글을 가져옴
        Optional<PictureBoardEntity> optionalPictureBoardEntity =  pictureBoardRepository.findById(id); // db로부터 가져오면 Optional형
        if(optionalPictureBoardEntity.isPresent())
            return PictureBoardDto.toPictureBoardDto(optionalPictureBoardEntity.get()); // Optional형으로부터 .get()을 통해 Entity만 추출
        else
            return null; // Id에 해당하는 게시글이 없으므로 null 반환
    }

    public void deleteById(Long id){ // 게시글의 Id를 바탕으로 db에서 해당 게시글을 삭제함
        PictureBoardEntity pictureBoardEntity = pictureBoardRepository.findById(id).get(); // db로부터 가져와서 Optional형으로부터 .get() 통해 Entity만 추출
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\" + pictureBoardEntity.getFileName(); // 해당 게시글에 대한 filePath를 가져옴
        File deleteFile = new File(filePath);
        deleteFile.delete(); // 삭제할 게시물의 이미지를 static 폴더에서 제거

        pictureBoardRepository.deleteById(id); // db에서 id에 해당하는 게시글을 삭제
    }
}