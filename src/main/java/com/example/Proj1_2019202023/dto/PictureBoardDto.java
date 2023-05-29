package com.example.Proj1_2019202023.dto;

import com.example.Proj1_2019202023.entity.PictureBoardEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PictureBoardDto {

    private Long id;
    private String title;
    private String comment;
    private String fileName;
    private String filePath;

    public static PictureBoardDto toPictureBoardDto(PictureBoardEntity pictureBoardEntity){
        PictureBoardDto pictureBoardDto = new PictureBoardDto();
        pictureBoardDto.setId(pictureBoardEntity.getId());
        pictureBoardDto.setTitle(pictureBoardEntity.getTitle());
        pictureBoardDto.setComment(pictureBoardEntity.getComment());
        pictureBoardDto.setFileName(pictureBoardEntity.getFileName());
        pictureBoardDto.setFilePath(pictureBoardEntity.getFilePath());
        return pictureBoardDto;
    }
}