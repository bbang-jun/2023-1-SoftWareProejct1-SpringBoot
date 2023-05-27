package com.example.Proj1_2019202023.repository;

import com.example.Proj1_2019202023.entity.PictureBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureBoardRepository extends JpaRepository<PictureBoardEntity, Long> {
}
