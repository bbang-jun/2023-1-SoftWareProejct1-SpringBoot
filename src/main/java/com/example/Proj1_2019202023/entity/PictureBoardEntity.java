package com.example.Proj1_2019202023.entity;

import com.example.Proj1_2019202023.dto.PictureBoardDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Data
@Table(name = "Proj1_DB_2019202023")
public class PictureBoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private String comment;

    @Column(length = 1000)
    private String fileName;

    @Column(length = 1000)
    private String filePath;

    public static PictureBoardEntity toBoardEntity(PictureBoardDto pictureBoardDto){
        PictureBoardEntity pictureBoardEntity = new PictureBoardEntity();
        pictureBoardEntity.setTitle(pictureBoardDto.getTitle());
        pictureBoardEntity.setComment(pictureBoardDto.getComment());
        pictureBoardEntity.setFileName(pictureBoardDto.getFileName());
        pictureBoardEntity.setFilePath(pictureBoardDto.getFilePath());

        return pictureBoardEntity;
    }
}
