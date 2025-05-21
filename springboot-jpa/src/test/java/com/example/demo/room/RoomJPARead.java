package com.example.demo.room;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Room;
import com.example.demo.repository.RoomRepository;

@SpringBootTest
public class RoomJPARead {
	@Autowired
	private RoomRepository roomRepository;
	
	@Test
	public void test() {
		//List<Room> rooms = roomRepository.findByRoomSizeGreaterThan(30);
		//List<Room> rooms = roomRepository.findRooms(30);
		List<Room> rooms = roomRepository.readRooms(30);
		System.out.println(rooms.size());
		System.out.println(rooms);
		
		
	}
}
