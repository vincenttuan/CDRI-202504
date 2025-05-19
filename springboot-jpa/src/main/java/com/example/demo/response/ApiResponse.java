package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 建立 Server 與 Client 在傳遞資料上的統一結構與標準(含錯誤)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
	private int status;     // 狀態 例如: 200, 400
	private String message; // 訊息 例如: 查詢成功, 新增成功, 請求錯誤
	private T data; 	    // payload 實際資料
	
	// 成功回應
	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<T>(200, message, data);
	}
	
	// 失敗回應
	public static <T> ApiResponse<T> error(int status, String message) {
		return new ApiResponse<T>(status, message, null);
	}
}
