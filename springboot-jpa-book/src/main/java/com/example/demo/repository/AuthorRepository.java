package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
	
	@Query("select a from Author a left join fetch a.books")
	List<Author> findAllWithBooks();
}
