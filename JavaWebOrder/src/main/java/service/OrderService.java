package service;

import java.util.ArrayList;
import java.util.List;

import dao.OrderDAO;
import model.dto.OrderDTO;
import model.entity.Order;

public class OrderService {
	private OrderDAO orderDAO = new OrderDAO();
	
	// 根據訂單項目(item)新增一筆訂單並回傳訂單顯示資訊(OrderDTO)
	public OrderDTO addOrder(String item) {
		// 1: 根據訂單項目(item)新增一筆訂單
		Order order = new Order();
		order.setItem(item);
		order.setPrice(100); // 價格一律 100 元
		// 傳給 orderDAO 儲存訂單
		orderDAO.save(order);
		//-------------------------------------
		// 2: 回傳訂單顯示資訊(OrderDTO)
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setMessage("您點了 " + order.getItem() + " 價格:" + order.getPrice() + "元");
		
		return orderDTO;
	}
	
	// 取得歷史資料
	public List<OrderDTO> getOrderHistory() {
		List<Order> orders = orderDAO.findAll(); // 取得所有資料
		// 將 List<Order> 轉 List<OrderDTO>
		List<OrderDTO> orderDTOs = new ArrayList<>();
		// 一筆一筆轉
		for(Order order : orders) {
			OrderDTO dto = new OrderDTO();
			dto.setMessage("您點了 " + order.getItem() + " 價格:" + order.getPrice() + "元");
			orderDTOs.add(dto); // 逐筆加入到集合中
		}
		return orderDTOs;
	}
	
	
}
