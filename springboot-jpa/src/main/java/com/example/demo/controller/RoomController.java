package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.exception.RoomException;
import com.example.demo.model.dto.RoomDto;
import com.example.demo.service.RoomService;

import jakarta.validation.Valid;

/**
 * Method URI            功能
 * --------------------------------------------------------------------
 * GET    /rooms                查詢所有會議室(多筆)
 * GET    /room/{roomId}        查詢指定會議室(單筆)-修改專用
 * POST   /room                 新增會議室
 * PUT    /room/update/{roomId} 完整修改會議室(同時修改 roomName 與 roomSize)
 * DELETE /room/delete/{roomId} 刪除會議室
 * --------------------------------------------------------------------
 * */

@Controller
@RequestMapping(value = {"/room", "/rooms"})
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@GetMapping
	public String getRooms(Model model, @ModelAttribute RoomDto roomDto) {
		List<RoomDto> roomDtos = roomService.findAllRooms();
		model.addAttribute("roomDtos", roomDtos);
		return "room/room";
	}
	
	@GetMapping("/{roomId}")
	public String getRoom(@PathVariable Integer roomId, Model model) {
		RoomDto roomDto = roomService.getRoomById(roomId);
		model.addAttribute("roomDto", roomDto);
		return "room/room_update";
	}
	
	/*
	 * @Valid RoomDto roomDto, BindingResult bindingResult
	 * RoomDto 要進行屬性資料驗證, 驗證結果放到 bindingResult
	 * */
	@PostMapping
	public String addRoom(@Valid RoomDto roomDto, BindingResult bindingResult, Model model) {
		// 驗證資料
		if(bindingResult.hasErrors()) { // 若驗證時有錯誤發生
			model.addAttribute("roomDtos", roomService.findAllRooms());
			return "room/room";
		}
		
		// 進行新增
		roomService.addRoom(roomDto);
		return "redirect:/rooms";
	}
	
	@PutMapping("/update/{roomId}")
	public String updateRoom(@PathVariable Integer roomId, @Valid RoomDto roomDto, BindingResult bindingResult) {
		// 驗證資料
		if(bindingResult.hasErrors()) { // 若驗證時有錯誤發生
			return "room/room_update";
		}
		
		// 進行修改
		roomService.updateRoom(roomId, roomDto);
		return "redirect:/rooms";
	}
	
	@DeleteMapping("/delete/{roomId}")
	public String deleteRoom(@PathVariable Integer roomId) {
		roomService.deleteRoom(roomId);
		return "redirect:/rooms"; // 重導到 /rooms 頁面
	}
	
	@ExceptionHandler({RoomException.class})
	public String handleException(Exception e, Model model) {
		e.printStackTrace();
		model.addAttribute("message", e.getMessage());
		return "error";
	}
	
}
