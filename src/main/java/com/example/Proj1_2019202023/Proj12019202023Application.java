package com.example.Proj1_2019202023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.io.File;

@SpringBootApplication
public class Proj12019202023Application {

	public static void main(String[] args) {

		String staticFolderPath = "src/main/resources/static"; // static 폴더 경로

		File staticFolder = new File(staticFolderPath);
		File[] files = staticFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				file.delete();
			}
		}

		SpringApplication.run(Proj12019202023Application.class, args);
	}
}
