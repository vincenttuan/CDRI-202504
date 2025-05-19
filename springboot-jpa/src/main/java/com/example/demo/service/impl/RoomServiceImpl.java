package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.RoomNotFoundException;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.model.dto.RoomDto;
import com.example.demo.model.entity.Room;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private RoomMapper roomMapper;
	
	@Override
	public List<RoomDto> findAllRooms() {
		return roomRepository.findAll()   // room 集合
							 .stream()    // room 串流
							 .map(roomMapper::toDto) // roomDto 串流  .map(room -> roomMapper.toDto(room))
							 .toList();   // roomDto 集合
	}

	@Override
	public RoomDto getRoomById(Integer roomId) {
		Room room = roomRepository.findById(roomId)
				.orElseThrow(() -> new RoomNotFoundException("找不到會議室: roodId=" + roomId));
		return roomMapper.toDto(room);
	}

	@Override
	public void addRoom(RoomDto roomDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRoom(Integer roomId, String roomName, Integer roomSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRoom(Integer roomId, RoomDto roomDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRoom(Integer roomId, String roomName, Integer roomSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRoom(Integer roomId) {
		// TODO Auto-generated method stub
		
	}

}
