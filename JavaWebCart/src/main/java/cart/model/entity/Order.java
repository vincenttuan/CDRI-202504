package cart.model.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Order {
	private Integer orderId;
	private Integer userId;
	private Date orderDate;
}
