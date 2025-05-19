package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.RoomDto;
import com.example.demo.model.entity.Room;

@Component // 此物件由 Springboot 來管理
public class RoomMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RoomDto toDto(Room room) {
		// Entity 轉 DTO
		return modelMapper.map(room, RoomDto.class);
	}
	
	public Room toEntity(RoomDto roomDto) {
		// DTO 轉 Entity
		return modelMapper.map(roomDto, Room.class);
	}
	
}
