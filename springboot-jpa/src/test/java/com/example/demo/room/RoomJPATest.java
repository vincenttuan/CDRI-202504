package com.example.demo.room;

import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Room;
import com.example.demo.repository.RoomRepository;

@SpringBootTest
public class RoomJPATest {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Test
	public void testRoomAdd() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("請輸入房號:");
		Integer roomId = scanner.nextInt();
		System.out.print("請輸入房名:");
		String roomName = scanner.next();
		System.out.print("請輸入人數:");
		Integer roomSize = scanner.nextInt();
		// --------------------------------------
		Room room = new Room(roomId, roomName, roomSize);
		// 新增到資料庫
		room = roomRepository.save(room);
		System.out.println("測試新增:" + room);
	}
	
}
