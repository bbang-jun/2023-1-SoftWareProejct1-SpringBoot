package com.example.Proj1_2019202023.service;

import com.example.Proj1_2019202023.repository.PictureBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureBoardService {

    private final PictureBoardRepository pictureBoardRepository;

    @Autowired
    public PictureBoardService(PictureBoardRepository pictureBoardRepository){this.pictureBoardRepository = pictureBoardRepository;}

}
