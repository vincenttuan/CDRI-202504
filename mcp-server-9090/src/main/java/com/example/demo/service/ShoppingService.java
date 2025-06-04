package com.example.demo.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShoppingService {

    // 商品資訊：商品名稱 -> 商品物件
    private static final Map<String, Product> productCatalog = new HashMap<>();
    static {
        productCatalog.put("蘋果", new Product("蘋果", 30));
        productCatalog.put("香蕉", new Product("香蕉", 20));
        productCatalog.put("橘子", new Product("橘子", 25));
        productCatalog.put("牛奶", new Product("牛奶", 60));
        productCatalog.put("麵包", new Product("麵包", 45));
    }

    // 購物車：商品名稱 -> 數量
    private Map<String, Integer> cart = new HashMap<>();

    /** 加入商品到購物車 */
    @Tool(name = "addToCart", description = "將指定商品加入購物車")
    public String addToCart(
            @ToolParam(description = "商品名稱") String product,
            @ToolParam(description = "數量") Integer quantity) {
        if (!productCatalog.containsKey(product)) {
            return "找不到此商品：" + product;
        }
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
        cart.forEach((product, qty) -> {
            Product prod = productCatalog.get(product);
            sb.append(product)
              .append(" x").append(qty)
              .append(" (單價：").append(prod.getPrice()).append("元)\n");
        });
        return sb.toString();
    }

    /** 結帳 */
    @Tool(name = "checkout", description = "購物車結帳")
    public String checkout() {
        System.out.println("呼叫 checkout()");
        if (cart.isEmpty()) {
            return "購物車是空的，無法結帳。";
        }
        int total = cart.entrySet().stream()
                .mapToInt(entry -> {
                    Product prod = productCatalog.get(entry.getKey());
                    return prod.getPrice() * entry.getValue();
                })
                .sum();
        cart.clear();
        return "結帳成功！總金額：" + total + " 元。";
    }

    /** 商品類別 */
    static class Product {
        private String name;
        private int price;

        public Product(String name, int price) {
            this.name = name;
            this.price = price;
        }
        public String getName() {
            return name;
        }
        public int getPrice() {
            return price;
        }
    }
}
