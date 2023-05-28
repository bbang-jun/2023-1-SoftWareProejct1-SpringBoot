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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PictureBoardService {

    private final PictureBoardRepository pictureBoardRepository;

    @Autowired
    public PictureBoardService(PictureBoardRepository pictureBoardRepository){this.pictureBoardRepository = pictureBoardRepository;}

    @Transactional
    public void create(PictureBoardDto pictureBoardDto, MultipartFile file) throws Exception{
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(filePath, fileName);
        file.transferTo(saveFile);

        pictureBoardDto.setFileName(fileName);
        pictureBoardDto.setFilePath("/static/" + fileName);
        PictureBoardEntity pictureBoardEntity = PictureBoardEntity.toBoardEntity(pictureBoardDto);
        pictureBoardRepository.save(pictureBoardEntity);
    }

    public List<PictureBoardDto> findAll(){
        List<PictureBoardEntity> pictureBoardEntityList = pictureBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<PictureBoardDto> pictureBoardDtoList = new ArrayList<>();
        for(PictureBoardEntity pictureBoardEntity : pictureBoardEntityList){
            pictureBoardDtoList.add(PictureBoardDto.toPictureBoardDto(pictureBoardEntity));
        }
        return pictureBoardDtoList;
    }

    public PictureBoardDto findById(Long id){
        Optional<PictureBoardEntity> optionalPictureBoardEntity =  pictureBoardRepository.findById(id);
        if(optionalPictureBoardEntity.isPresent())
            return PictureBoardDto.toPictureBoardDto(optionalPictureBoardEntity.get());
        else
            return null;
    }

    public void deleteById(Long id){
        pictureBoardRepository.deleteById(id);
    }

    public void update(PictureBoardDto pictureBoardDto, MultipartFile file) throws Exception{
        Optional<PictureBoardEntity> pictureBoardOptional = pictureBoardRepository.findById(pictureBoardDto.getId());
        if (pictureBoardOptional.isPresent()) {

            PictureBoardEntity pictureBoardEntity = pictureBoardOptional.get();

            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(filePath, fileName);
            file.transferTo(saveFile);

            pictureBoardEntity.setTitle(pictureBoardDto.getTitle());
            pictureBoardEntity.setComment(pictureBoardDto.getComment());
            pictureBoardEntity.setFileName(fileName);
            pictureBoardEntity.setFilePath("/static/" + fileName);


            pictureBoardRepository.save(pictureBoardEntity);
        } else {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + pictureBoardDto.getId());
        }
    }
}
