package case03;

import java.util.Map;

// 主餐
public class MainDish {
	
	private String name; // 主餐名稱
	private int price; // 價格
	// 價格表
	private static final Map<String, Integer> PRICE_MAP = Map.of("剉冰", 50, "豆花", 40);
	
	public MainDish(String name) {
		this.name = name;
		this.price = PRICE_MAP.get(name);
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}
	
}
