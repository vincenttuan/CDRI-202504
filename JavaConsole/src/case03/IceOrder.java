package case03;

public class IceOrder {
	private MainDish mainDish; // 主餐
	private Topping topping; // 配料
	private int totalPrice; // 總價
	
	public IceOrder(String mainDishName, String[] toppingArray) {
		this(new MainDish(mainDishName), new Topping(toppingArray));
	}
	
	public IceOrder(MainDish mainDish, Topping topping) {
		this.mainDish = mainDish;
		this.topping = topping;
		this.totalPrice = mainDish.getPrice() + topping.calculateToppingPrice();
	}

	public MainDish getMainDish() {
		return mainDish;
	}

	public Topping getTopping() {
		return topping;
	}

	public int getTotalPrice() {
		return totalPrice;
	}
	
	
}
