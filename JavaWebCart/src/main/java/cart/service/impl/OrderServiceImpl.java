package cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import cart.dao.OrderDAO;
import cart.dao.impl.OrderDAOImpl;
import cart.model.dto.OrderDTO;
import cart.model.dto.OrderItemDTO;
import cart.model.dto.ProductDTO;
import cart.model.entity.Order;
import cart.model.entity.OrderItem;
import cart.service.OrderService;

public class OrderServiceImpl implements OrderService {
	
	private OrderDAO orderDAO = new OrderDAOImpl();
	
	@Override
	public void addOrder(Integer userId, List<ProductDTO> cart) {
		Integer quantity = 1; // 固定數量(Homework:如何數量可以調整)
		// 新增訂單主檔後可以得到 orderId
		Integer orderId = orderDAO.addOrder(userId);
		// 逐一新增訂單明細紀錄
		for(ProductDTO productDTO : cart) {
			orderDAO.addOrderItem(orderId, productDTO.getProductId(), quantity);
		}
	}

	@Override
	public List<OrderDTO> findAllOrdersByUserId(Integer userId) {
		List<OrderDTO> orderDTOs = new ArrayList<>();
		// 取得該使用者的訂單主檔資訊
		List<Order> orders = orderDAO.findAllOrdersByUserId(userId);
		for(Order order : orders) {
			// OrderDTO Mapping
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setOrderId(order.getOrderId());
			orderDTO.setUserId(order.getUserId());
			orderDTO.setOrderDate(order.getOrderDate());
			// 明細
			for(OrderItem item : orderDAO.findAllOrderItemsByOrderId(order.getOrderId())) {
				// OrderItem 轉 OrderItemDTO
				OrderItemDTO itemDTO = new OrderItemDTO();
				itemDTO.setItemId(item.getItemId());
				itemDTO.setOrderId(item.getOrderId());
				itemDTO.setProductId(item.getProductId());
				itemDTO.setQuantity(item.getQuantity());
				// 注入
				orderDTO.getItems().add(itemDTO);
			}
		}
		return orderDTOs;
	}

}
