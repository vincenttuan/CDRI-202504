package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Room;

// Spring JPA
@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> { // Room: entity, Integer: @Id 的型別
	// 預設會實現 CRUD
	// 自定義查詢
	// 1. 查詢 roomSize 大於指定值得房間(自動生成 SQL)
	List<Room> findByRoomSizeGreaterThan(Integer size);
}
