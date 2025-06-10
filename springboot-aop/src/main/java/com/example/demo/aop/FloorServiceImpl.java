package com.example.demo.aop;

import org.springframework.stereotype.Service;

@Service
public class FloorServiceImpl implements FloorService {

	@Override
	public String enterFloor(String username, int floor) {
		// 業務邏輯
		return username + " 成功進入 " + floor + " 樓";
	}
	
}
