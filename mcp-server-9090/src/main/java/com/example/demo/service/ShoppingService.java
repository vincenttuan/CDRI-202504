package com.example.demo.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShoppingService {

    private Map<String, Integer> cart = new HashMap<>();

    /** 加入商品到購物車 */
    @Tool(name = "addToCart", description = "將指定商品加入購物車")
    public String addToCart(
            @ToolParam(description = "商品名稱") String product,
            @ToolParam(description = "數量") Integer quantity) {
        cart.put(product, cart.getOrDefault(product, 0) + quantity);
        System.out.println("呼叫 addToCart() => " + product + " x" + quantity);
        return product + " 已加入購物車，數量：" + cart.get(product);
    }

    /** 查詢購物車內容 */
    @Tool(name = "viewCart", description = "查詢購物車目前所有商品")
    public String viewCart() {
        System.out.println("呼叫 viewCart()");
        if (cart.isEmpty()) {
            return "購物車目前是空的。";
        }
        StringBuilder sb = new StringBuilder("購物車內容：\n");
        cart.forEach((product, qty) -> sb.append(product).append(" x").append(qty).append("\n"));
        return sb.toString();
    }

    /** 結帳 */
    @Tool(name = "checkout", description = "購物車結帳")
    public String checkout() {
        System.out.println("呼叫 checkout()");
        if (cart.isEmpty()) {
            return "購物車是空的，無法結帳。";
        }
        int total = cart.values().stream().mapToInt(qty -> qty * 100).sum(); // 假設每件商品100元
        cart.clear();
        return "結帳成功！總金額：" + total + " 元。";
    }
}
