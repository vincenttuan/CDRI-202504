package com.example.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 書籍
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
	@Id
	private Integer bookId; // 書號
	private String bookName; // 書名
	private Integer bookPrice; // 價格
}
