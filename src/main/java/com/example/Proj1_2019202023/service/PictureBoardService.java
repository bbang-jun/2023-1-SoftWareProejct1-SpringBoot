package com.example.Proj1_2019202023.service;

import com.example.Proj1_2019202023.dto.PictureBoardDto;
import com.example.Proj1_2019202023.entity.PictureBoardEntity;
import com.example.Proj1_2019202023.repository.PictureBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PictureBoardService {

    private final PictureBoardRepository pictureBoardRepository;

    @Autowired
    public PictureBoardService(PictureBoardRepository pictureBoardRepository){this.pictureBoardRepository = pictureBoardRepository;}

    public void create(PictureBoardDto pictureBoardDto){
        PictureBoardEntity pictureBoardEntity = PictureBoardEntity.toBoardEntity(pictureBoardDto);
        pictureBoardRepository.save(pictureBoardEntity);
    }

    public List<PictureBoardDto> findAll(){
        List<PictureBoardEntity> pictureBoardEntityList = pictureBoardRepository.findAll();
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

    public void update(PictureBoardDto pictureBoardDto){
        Optional<PictureBoardEntity> pictureBoardOptional = pictureBoardRepository.findById(pictureBoardDto.getId());
        if (pictureBoardOptional.isPresent()) {
            PictureBoardEntity board = pictureBoardOptional.get();
            board.setTitle(pictureBoardDto.getTitle());
            board.setComment(pictureBoardDto.getComment());
            pictureBoardRepository.save(board);
        } else {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + pictureBoardDto.getId());
        }
    }
}
