package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.RoomDto;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.RoomService;

/**
請求方法 URL 路徑              功能說明      請求參數                                   回應
--------------------------------------------------------------------------------------------------------------------
GET    /rest/room          取得所有房間列表 無                                       成功時返回所有房間的列表 payload 及成功訊息。
GET    /rest/room/{roomId} 取得指定房間資料 roomId (路徑參數，房間 ID)                  成功時返回指定房間資料及 payload 成功訊息。
POST   /rest/room          新增房間       請求體包含 roomDto                         成功時返回成功訊息，並包含 payload。
PUT    /rest/room/{roomId} 更新指定房間資料 roomId (路徑參數，房間 ID)，請求體包含 roomDto 成功時返回成功訊息，並包含 payload。
DELETE /rest/room/{roomId} 刪除指定房間    roomId (路徑參數，房間 ID)                  成功時返回成功訊息，不包含 payload。
*/

@RestController
@RequestMapping("/rest/room")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class RoomRestController {
	
	@Autowired
	private RoomService roomService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<RoomDto>>> findAllRooms() {
		List<RoomDto> roomDtos = roomService.findAllRooms(); // payload
		String message = roomDtos.isEmpty() ? "查無資料" : "查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, roomDtos));
	}
	
	
}
