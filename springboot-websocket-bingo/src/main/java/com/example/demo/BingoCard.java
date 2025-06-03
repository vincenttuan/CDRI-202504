package com.example.demo;

import lombok.Data;
import java.util.List;

@Data
public class BingoCard {
    private String playerId;         // 玩家唯一ID
    private List<Integer> numbers;   // 5x5賓果卡（1~25隨機排列）
}
