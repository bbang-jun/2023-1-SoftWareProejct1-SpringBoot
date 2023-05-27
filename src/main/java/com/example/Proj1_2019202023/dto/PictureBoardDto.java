package com.example.Proj1_2019202023.dto;

import com.example.Proj1_2019202023.entity.PictureBoardEntity;
import lombok.*;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PictureBoardDto {

    private Long id;
    private Long title;
    private Long comment;

    public static PictureBoardDto toPictureBoardDto(PictureBoardEntity pictureBoardEntity){
        PictureBoardDto pictureBoardDto = new PictureBoardDto();
        pictureBoardDto.setId(pictureBoardEntity.getId());
        pictureBoardDto.setTitle(pictureBoardEntity.getTitle());
        pictureBoardDto.setComment(pictureBoardEntity.getComment());
        return pictureBoardDto;
    }

    public static List<PictureBoardDto> toPictureBoardDtoList(List<PictureBoardEntity> pictureBoardEntityList){
        List<PictureBoardDto> pictureBoardDtoList = new ArrayList<>();
        for(PictureBoardEntity pictureBoardEntity : pictureBoardEntityList){
            pictureBoardDtoList.add(toPictureBoardDto(pictureBoardEntity));
        }

        return pictureBoardDtoList;
    }

}
