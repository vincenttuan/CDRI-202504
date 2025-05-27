package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	
	// 取得書本價格
	@Query(value = "select book_price from book where book_id = :bookId", nativeQuery = true)
	Integer getBookPrice(Integer bookId);
	
}
