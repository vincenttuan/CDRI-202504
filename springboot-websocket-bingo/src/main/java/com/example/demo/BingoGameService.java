package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class BingoGameService {
    private final Map<String, BingoCard> playerCards = new HashMap<>();
    private final Set<Integer> selectedNumbers = new HashSet<>();
    private final List<String> playerOrder = new ArrayList<>(); // 玩家順序
    private int currentPlayerIndex = 0; // 目前輪到的玩家索引
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> currentTimer = null;
    private Runnable timeoutTask = null;
    // 產生新玩家賓果卡
    public BingoCard createCardForPlayer(String playerId) {
        
    	List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 99; i++) nums.add(i);
        Collections.shuffle(nums);           // 打亂
        nums = nums.subList(0, 25);          // 取前25個作為賓果卡號碼
        
        BingoCard card = new BingoCard();
        card.setPlayerId(playerId);
        card.setNumbers(nums);
        playerCards.put(playerId, card);
        
        // 新玩家加入遊戲順序
        if (!playerOrder.contains(playerId)) {
            playerOrder.add(playerId);
        }
        return card;
    }

    // 處理選數字（檢查是否輪到該玩家）
    public boolean selectNumber(int number, String playerId) {
        // 檢查是否輪到該玩家
        if (!isPlayerTurn(playerId)) {
            return false;
        }
        // 檢查數字是否已被選過
        if (selectedNumbers.contains(number)) {
            return false;
        }
        selectedNumbers.add(number);
        nextPlayer(); // 切換到下一位玩家
        return true;
    }

    // 檢查是否輪到該玩家
    public boolean isPlayerTurn(String playerId) {
        if (playerOrder.isEmpty()) return false;
        return playerOrder.get(currentPlayerIndex).equals(playerId);
    }

    // 切換到下一位玩家
    public void nextPlayer() {
        if (playerOrder.size() > 0) {
            currentPlayerIndex = (currentPlayerIndex + 1) % playerOrder.size();
        }
    }
    
    // 取得所有玩家
    public List<String> findAllPlayers() {
    	return playerOrder;
    }
    
    // 取得當前輪到的玩家
    public String getCurrentPlayer() {
        if (playerOrder.isEmpty()) return null;
        return playerOrder.get(currentPlayerIndex);
    }

    // 取得遊戲狀態
    public Map<String, Object> getGameState() {
        Map<String, Object> state = new HashMap<>();
        state.put("selectedNumbers", selectedNumbers);
        state.put("currentPlayer", getCurrentPlayer());
        state.put("playerOrder", playerOrder);
        return state;
    }
    
    // 檢查名稱是否已存在
    public boolean isPlayerIdTaken(String playerId) {
        return playerCards.containsKey(playerId);
    }
    
    // 每次輪到新玩家時呼叫
    public void startTurnTimer(Runnable timeoutCallback) {
        // 取消前一個計時器
        if (currentTimer != null && !currentTimer.isDone()) {
            currentTimer.cancel(true);
        }
        // 啟動新計時器
        timeoutTask = timeoutCallback;
        currentTimer = scheduler.schedule(timeoutCallback, 5, TimeUnit.SECONDS);
    }

    // 玩家出牌時呼叫，取消計時器
    public void cancelTurnTimer() {
        if (currentTimer != null && !currentTimer.isDone()) {
            currentTimer.cancel(true);
        }
    }
    
    public BingoCard getCard(String playerId) {
        return playerCards.get(playerId);
    }

    public Set<Integer> getSelectedNumbers() {
        return selectedNumbers;
    }

    public void reset() {
        playerCards.clear();
        selectedNumbers.clear();
        playerOrder.clear();
        currentPlayerIndex = 0;
    }
}
