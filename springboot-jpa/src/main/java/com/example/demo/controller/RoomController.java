package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.dto.RoomDto;
import com.example.demo.service.RoomService;

/**
 * Method URI            功能
 * --------------------------------------------------------------------
 * GET    /rooms                查詢所有會議室(多筆)
 * GET    /room/{roomId}        查詢指定會議室(單筆)
 * POST   /room                 新增會議室
 * POST   /room/update/{roomId} 完整修改會議室(同時修改 roomName 與 roomSize)
 * GET    /room/delete/{roomId} 刪除會議室
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
	
	@PostMapping
	public String addRoom(RoomDto roomDto) {
		roomService.addRoom(roomDto);
		return "redirect:/rooms";
	}
	
	
}
