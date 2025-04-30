package cart.model.entity;

import lombok.Data;

@Data
public class OrderItem {
	private Integer itemId;
	private Integer orderId;
	private Integer productId;
	private Integer quantity;
}
