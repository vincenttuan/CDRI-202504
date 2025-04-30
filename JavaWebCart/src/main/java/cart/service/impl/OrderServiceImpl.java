package cart.service.impl;

import java.util.List;

import cart.dao.OrderDAO;
import cart.dao.impl.OrderDAOImpl;
import cart.model.dto.OrderDTO;
import cart.model.dto.ProductDTO;
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
		// TODO Auto-generated method stub
		return null;
	}

}
