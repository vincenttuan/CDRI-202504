package cart.service;

import java.util.List;

import cart.model.dto.OrderDTO;
import cart.model.dto.ProductDTO;

public interface OrderService {
	// 建立訂單(使用者 id, 購物車資料)
	void addOrder(Integer userId, List<ProductDTO> cart);
	// 查詢使用者的訂單紀錄
	List<OrderDTO> findAllOrdersByUserId(Integer userId);
}
