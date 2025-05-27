package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Stock;

import jakarta.transaction.Transactional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
	
	// 取得書本庫存數量
	@Query(value = "select book_amount from stock where book_id = :bookId", nativeQuery = true)
	Integer getBookAmount(Integer bookId);
	
	// 更新庫存數量
	@Modifying
	@Transactional
	@Query(value = "update stock set book_amount = book_amount - :amountToReduce where book_id = :bookId", nativeQuery = true)
	void updateBookAmount(Integer amountToReduce, Integer bookId);
}
