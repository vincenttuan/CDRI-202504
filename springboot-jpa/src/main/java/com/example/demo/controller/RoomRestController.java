package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.RoomException;
import com.example.demo.model.dto.RoomDto;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.RoomService;

import jakarta.validation.Valid;

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
@RequestMapping(value = {"/rest/room", "/rest/rooms"})
// allowCredentials = "true" 允許接收客戶端傳來的憑證資料,例如: session id
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class RoomRestController {
	
	@Autowired
	private RoomService roomService;
	
	// 取得所有房間列表
	@GetMapping
	public ResponseEntity<ApiResponse<List<RoomDto>>> findAllRooms() {
		List<RoomDto> roomDtos = roomService.findAllRooms(); // payload
		String message = roomDtos.isEmpty() ? "查無資料" : "查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, roomDtos));
	}
	
	// 新增房間
	@PostMapping
	public ResponseEntity<ApiResponse<RoomDto>> addRoom(@Valid @RequestBody RoomDto roomDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			//return ResponseEntity.badRequest().body(ApiResponse.error(500, "新增失敗"));
			//return ResponseEntity.ok(ApiResponse.error(500, "新增失敗:" + bindingResult.getAllErrors().get(0).getDefaultMessage()));
			throw new RoomException("新增失敗:" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		roomService.addRoom(roomDto);
		return ResponseEntity.ok(ApiResponse.success("Room 新增成功", roomDto));
	}
	
	// 取得單筆
	@GetMapping("/{roomId}")
	public ResponseEntity<ApiResponse<RoomDto>> getRoom(@PathVariable Integer roomId) {
		RoomDto roomDto = roomService.getRoomById(roomId);
		return ResponseEntity.ok(ApiResponse.success("Room 查詢單筆成功", roomDto));
	}
	
	// 修改房間
	@PutMapping("/{roomId}")
	public ResponseEntity<ApiResponse<RoomDto>> updateRoom(@PathVariable Integer roomId, @Valid @RequestBody RoomDto roomDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new RoomException("修改失敗:" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		roomService.updateRoom(roomId, roomDto);
		return ResponseEntity.ok(ApiResponse.success("Room 修改成功", roomDto));
	}
	
	// 刪除房間
	@DeleteMapping("/{roomId}")
	public ResponseEntity<ApiResponse<Integer>> deleteRoom(@PathVariable Integer roomId) {
		roomService.deleteRoom(roomId);
		return ResponseEntity.ok(ApiResponse.success("Room 刪除成功", roomId));
	}
	
	// 錯誤處理
	@ExceptionHandler({RoomException.class})
	public ResponseEntity<ApiResponse<Void>> handleRoomExceptions(RoomException e) {
		return ResponseEntity.ok(ApiResponse.error(500, e.getMessage()));
	}
	
}
