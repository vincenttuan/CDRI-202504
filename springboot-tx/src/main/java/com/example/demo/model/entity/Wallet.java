package com.example.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 錢包
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
	
	@Id
	private String username; // 用戶
	private Integer balance; // 餘額
}
