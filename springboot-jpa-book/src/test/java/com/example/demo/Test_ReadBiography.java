package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Biography;
import com.example.demo.repository.BiographyRepository;

@SpringBootTest
public class Test_ReadBiography {
	@Autowired
	private BiographyRepository biographyRepository;
	
	@Test
	public void read() {
		//List<Biography> biographies = biographyRepository.findAll();
		List<Biography> biographies = biographyRepository.findAllWithAuthor();
		biographies.forEach(biography -> {
			System.out.printf("ID:%d 內容:%s 作者:%s%n"
					, biography.getId(), biography.getDetails(), biography.getAuthor().getName());
		});
		
	}
}
