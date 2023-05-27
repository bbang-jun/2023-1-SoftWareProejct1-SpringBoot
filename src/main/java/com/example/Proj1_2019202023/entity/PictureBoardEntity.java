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
@Table(name = "picture_board")
public class PictureBoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private String comment;

    public static PictureBoardEntity toBoardEntity(PictureBoardDto pictureBoardDto){
        PictureBoardEntity pictureBoardEntity = new PictureBoardEntity();
        pictureBoardEntity.setTitle(pictureBoardDto.getTitle());
        pictureBoardEntity.setComment(pictureBoardDto.getComment());

        return pictureBoardEntity;
    }
}
