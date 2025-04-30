package cart.dao.impl;

import java.util.List;

import cart.dao.OrderDAO;
import cart.model.entity.Order;
import cart.model.entity.OrderItem;

public class OrderDAOImpl extends BaseDao implements OrderDAO {

	@Override
	public Integer addOrder(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addOrderItem(Integer orderId, Integer productId, Integer quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Order> findAllOrdersByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderItem> findAllOrderItemsByOrderId(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
