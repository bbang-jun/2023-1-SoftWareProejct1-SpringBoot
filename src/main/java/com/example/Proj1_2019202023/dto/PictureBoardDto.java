package com.example.Proj1_2019202023.dto;

import com.example.Proj1_2019202023.entity.PictureBoardEntity;
import lombok.*;

@Getter // getter 함수를 쓸 수 있는 lombok annotation
@Setter // setter 함수를 쓸 수 있는 lombok annotation
@NoArgsConstructor // 인자 없는 생성자
@AllArgsConstructor // 필드에 대한 모든 인자를 가지고 있는 생성자
public class PictureBoardDto {

    private Long id; // 게시물 번호
    private String title; // 게시물 제목
    private String comment; // 게시물 내용
    private String fileName; // 파일 이름
    private String filePath; // 파일 경로

    public static PictureBoardDto toPictureBoardDto(PictureBoardEntity pictureBoardEntity){ // entity -> dto
        PictureBoardDto pictureBoardDto = new PictureBoardDto(); // dto 객체 생성
        pictureBoardDto.setId(pictureBoardEntity.getId());
        pictureBoardDto.setTitle(pictureBoardEntity.getTitle()); // 제목 저장
        pictureBoardDto.setComment(pictureBoardEntity.getComment()); // 내용 저장
        pictureBoardDto.setFileName(pictureBoardEntity.getFileName()); // 파일 이름 저장
        pictureBoardDto.setFilePath(pictureBoardEntity.getFilePath()); // 파일 경로 저장

        return pictureBoardDto; // 정보 저장 완료 후 dto 반환
    }
}