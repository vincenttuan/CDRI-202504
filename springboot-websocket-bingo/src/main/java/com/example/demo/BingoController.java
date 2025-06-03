package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import java.util.*;

@Controller
public class BingoController {

    @Autowired
    private BingoGameService bingoGameService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 玩家請求新卡片
    @MessageMapping("/bingo/newCard")
    public void newCard(@Payload String playerId) {
        // 檢查名稱是否重複
        if (bingoGameService.isPlayerIdTaken(playerId)) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "這個名字已經被使用，請換一個名字！");
            messagingTemplate.convertAndSend("/topic/bingoError/" + playerId, error);
            return;
        }
        /*
        if (bingoGameService.findAllPlayers().size() > 5) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "人數已經超過(最多 5 人)");
            messagingTemplate.convertAndSend("/topic/bingoError/" + playerId, error);
            return;
        }
        */
        BingoCard card = bingoGameService.createCardForPlayer(playerId);
        messagingTemplate.convertAndSend("/topic/bingo/" + playerId, card);
        broadcastGameState();
        // 如果玩家數量大於1且剛好輪到這個人，啟動計時器
        if (bingoGameService.isPlayerTurn(playerId)) {
            startTurnTimer();
        }
    }


    // 玩家選數字（需檢查輪流）
    @MessageMapping("/bingo/select")
    public void selectNumber(@Payload Map<String, Object> payload) {
        int number = (Integer) payload.get("number");
        String playerId = (String) payload.get("playerId");
        
        // 檢查是否成功選中數字（包含輪流檢查）
        boolean success = bingoGameService.selectNumber(number, playerId);
        
        if (success) {
        	bingoGameService.cancelTurnTimer(); // 玩家有行動，取消計時
            broadcastGameState();
            startTurnTimer(); // 換人後啟動新計時器
        } else {
            // 回傳錯誤訊息給該玩家
            Map<String, Object> error = new HashMap<>();
            error.put("error", "不是你的回合或數字已被選過！");
            messagingTemplate.convertAndSend("/topic/bingoError/" + playerId, error);
        }
    }
    
    // 啟動5秒計時器
    private void startTurnTimer() {
        bingoGameService.startTurnTimer(() -> {
            // 這裡是在5秒後自動執行
            bingoGameService.nextPlayer();
            broadcastGameState();
            startTurnTimer(); // 換人後繼續啟動下一個計時器
        });
    }
    
    // 廣播遊戲狀態給所有玩家
    private void broadcastGameState() {
        Map<String, Object> gameState = bingoGameService.getGameState();
        messagingTemplate.convertAndSend("/topic/bingoSync/all", gameState);
    }
}
