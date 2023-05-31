package com.example.Proj1_2019202023.entity;

import com.example.Proj1_2019202023.dto.PictureBoardDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity // entity로써의 sring bean으로 등록
@Getter // getter 함수를 쓸 수 있는 lombok annotation
@Setter // setter 함수를 쓸 수 있는 lombok annotation
@Data
@Table(name = "Proj1_DB_2019202023") // h2 db table 이름을 Proj1_DB_2019202023으로 설정
public class PictureBoardEntity {

    @Id // primary key (게시물의 구별할 수 있는 유일한 키)
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동으로 1씩 증가
    private Long id; // 게시물 번호

    @Column
    private String title; // 게시물 제목

    @Column
    private String comment; // 게시물 내용

    @Column(length = 1000)
    private String fileName; // 파일 이름

    @Column(length = 1000)
    private String filePath; // 파일 경로

    public static PictureBoardEntity toBoardEntity(PictureBoardDto pictureBoardDto){ // dto -> entity
        PictureBoardEntity pictureBoardEntity = new PictureBoardEntity(); // entity 객체 생성
        pictureBoardEntity.setId(pictureBoardDto.getId());
        pictureBoardEntity.setTitle(pictureBoardDto.getTitle()); // 제목 저장
        pictureBoardEntity.setComment(pictureBoardDto.getComment()); // 내용 저장
        pictureBoardEntity.setFileName(pictureBoardDto.getFileName()); // 파일 이름 저장
        pictureBoardEntity.setFilePath(pictureBoardDto.getFilePath()); // 파일 경로 저장

        return pictureBoardEntity; // 정보 저장 완료 후 entity 반환
    }
}