package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Biography;

@Repository
public interface BiographyRepository extends JpaRepository<Biography, Integer> {
	
}
