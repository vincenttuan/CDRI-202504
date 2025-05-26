package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Author;
import com.example.demo.model.entity.Biography;

@Repository
public interface BiographyRepository extends JpaRepository<Biography, Integer> {
	
	//@Query("select b from Biography b inner join fetch b.author") // Biography 與 Author 同時都有
	/*
	 * A  1
	 * B  2
	 * D  4
	 * */
	@Query("select b from Biography b left join fetch b.author") // 若沒有查到 Author 則該欄位顯示 null
	/*
	 * A  1
	 * B  2
	 * C  null
	 * D  4
	 * 
	 * */
	List<Biography> findAllWithAuthor();
}
