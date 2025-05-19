package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.RoomDto;

public interface RoomService {
	public List<RoomDto> findAllRooms(); // 查詢所有房間
	public RoomDto getRoomById(Integer roomId); // 查詢單筆房間
	public void addRoom(RoomDto roomDto); // 新增房間
	public void addRoom(Integer roomId, String roomName, Integer roomSize); // 新增房間
	public void updateRoom(Integer roomId, RoomDto roomDto); // 修改房間
	public void updateRoom(Integer roomId, String roomName, Integer roomSize); // 修改房間
	public void deleteRoom(Integer roomId); // 刪除房間
}
