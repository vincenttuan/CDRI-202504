package com.example.demo.model.dto;

import lombok.Data;
import lombok.ToString;

// UserDto 對應於 User Entity
// 屬性名稱可以與所對應的 entity 不同
@Data
public class UserDto {
	private Integer userId; // 使用者 Id
	private String username; // 使用者名稱
	private String email; // 使用者Email
	private Boolean active; // 帳號啟動
	private String role; // 角色權限
	
}
