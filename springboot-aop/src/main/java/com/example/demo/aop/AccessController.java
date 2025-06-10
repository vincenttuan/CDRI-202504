package com.example.demo.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class AccessController {
	// 用戶授權樓層的資料(In-Memory)
	private static final Map<String, Set<Integer>> userFloorMap = new HashMap<>();
	
	static {
		userFloorMap.put("alice", Set.of(1, 2, 3)); // 員工
		userFloorMap.put("guest", Set.of(1)); // 訪客
		userFloorMap.put("security", Set.of(1, 2, 3, 4, 5)); // 保全
	}
	
	public boolean hasAccess(String username, int floor) {
		return userFloorMap.getOrDefault(username, Set.of()).contains(floor);
	}
}
