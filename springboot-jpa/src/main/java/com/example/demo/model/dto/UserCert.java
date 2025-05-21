package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

// 使用者憑證
// 登入成功之後會得到的憑證資料(只有 Getter)
@AllArgsConstructor
@Getter
@ToString
public class UserCert {
	private Integer userId; // 使用者 Id
	private String username; // 使用者名稱
	private String role; // 角色權限
}
