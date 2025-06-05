package com.example.demo.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockOrderService {

    // 股票資訊：股票代號 -> 股票物件
    private static final Map<String, Stock> stockMarket = new HashMap<>();
    static {
        stockMarket.put("2330", new Stock("2330", "台積電", 850));
        stockMarket.put("2317", new Stock("2317", "鴻海", 120));
        stockMarket.put("2454", new Stock("2454", "聯發科", 1200));
        stockMarket.put("2881", new Stock("2881", "富邦金", 70));
        stockMarket.put("2303", new Stock("2303", "聯電", 55));
    }

    // 預設持股：股票代號 -> 持有股數
    private Map<String, Integer> portfolio = new HashMap<>();
    {
        portfolio.put("2330", 20);  // 台積電 20 股
        portfolio.put("2454", 10);  // 聯發科 10 股
        portfolio.put("2317", 50);  // 鴻海 50 股
        portfolio.put("2881", 30);  // 富邦金 30 股
    }

    /** 買入股票 */
    @Tool(name = "buyStock", description = "買入指定股票")
    public String buyStock(
            @ToolParam(description = "股票代號") String symbol,
            @ToolParam(description = "股數") Integer quantity) {
        if (!stockMarket.containsKey(symbol)) {
            return "找不到此股票代號：" + symbol;
        }
        portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
        System.out.println("[DEBUG] 呼叫 buyStock() => " + symbol + " x" + quantity);
        Stock stock = stockMarket.get(symbol);
        int cost = stock.getPrice() * quantity;
        return stockMarket.get(symbol).getName() + " 已買入，持有股數：" + portfolio.get(symbol) + "，買進成本：" + cost;
    }

    /** 查詢持股 */
    @Tool(name = "viewPortfolio", description = "查詢目前持有的所有股票")
    public String viewPortfolio() {
        System.out.println("[DEBUG] 呼叫 viewPortfolio()");
        if (portfolio.isEmpty()) {
            return "目前沒有持有任何股票。";
        }
        StringBuilder sb = new StringBuilder("📊 持股明細：\n");
        portfolio.forEach((symbol, qty) -> {
            Stock stock = stockMarket.get(symbol);
            sb.append("▸ ")
              .append(stock.getName()).append(" (").append(symbol).append(")")
              .append("\n   數量: ").append(qty)
              .append(" 股 | 現價: ").append(stock.getPrice()).append("元\n");
        });
        return sb.toString();
    }

    /** 賣出股票 */
    @Tool(name = "sellStock", description = "賣出持有的股票")
    public String sellStock(
            @ToolParam(description = "股票代號") String symbol,
            @ToolParam(description = "股數") Integer quantity) {
        if (!portfolio.containsKey(symbol) || portfolio.get(symbol) < quantity) {
            return "❌ 持有股數不足，無法賣出 " + symbol + "。";
        }
        portfolio.put(symbol, portfolio.get(symbol) - quantity);
        if (portfolio.get(symbol) == 0) {
            portfolio.remove(symbol);
        }
        int amount = stockMarket.get(symbol).getPrice() * quantity;
        System.out.println("[DEBUG] 呼叫 sellStock() => " + symbol + " x" + quantity);
        return "✅ 已賣出 " + stockMarket.get(symbol).getName() 
             + " " + quantity + " 股，成交金額：" + amount + " 元。";
    }

    /** 股票類別 */
    static class Stock {
        private String symbol;
        private String name;
        private int price;

        public Stock(String symbol, String name, int price) {
            this.symbol = symbol;
            this.name = name;
            this.price = price;
        }
        public String getSymbol() { return symbol; }
        public String getName() { return name; }
        public int getPrice() { return price; }
    }
}


