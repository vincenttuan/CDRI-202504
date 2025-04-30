package cart.model.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
	private Integer itemId;
	private Integer orderId;
	private Integer productId;
	private Integer quantity;
}
