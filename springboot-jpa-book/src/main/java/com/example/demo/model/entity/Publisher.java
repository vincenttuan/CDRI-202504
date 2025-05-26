package com.example.demo.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class Publisher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 50, nullable = false)
	private String name;
	
	@ManyToMany
	@JoinTable(
			name = "publisher_book",
			joinColumns = @JoinColumn(name = "publisher_id"),
			inverseJoinColumns = @JoinColumn(name = "book_id")
	)
	private List<Book> books;
	
	// 自建一個新增書籍的方法
	public void addBook(Book book) {
		books.add(book);
	}
}
