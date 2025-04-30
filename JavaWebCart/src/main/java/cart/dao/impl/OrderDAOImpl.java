package cart.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import cart.dao.OrderDAO;
import cart.model.entity.Order;
import cart.model.entity.OrderItem;

public class OrderDAOImpl extends BaseDao implements OrderDAO {

	@Override
	public Integer addOrder(Integer userId) {
		String sql = "insert into `order` (user_id) values(?)";
		Integer orderId = null; // 存放新增後的 order_id 資料
		// 因為後續要取得新增後自動生成的 order_id 所以要加上 Statement.RETURN_GENERATED_KEYS 參數設定
		try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pstmt.setInt(1, userId);
			pstmt.executeUpdate(); // 執行更新
			
			// 取得 order_id
			ResultSet generateKeys = pstmt.getGeneratedKeys();
			if(generateKeys.next()) { // 有得到 key 資料
				orderId = generateKeys.getInt(1); // 取得新增後自動生成的 order_id
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderId;
	}

	@Override
	public void addOrderItem(Integer orderId, Integer productId, Integer quantity) {
		String sql = "insert into order_item(order_id, product_id, quantity) values(?, ?, ?)";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, orderId);
			pstmt.setInt(2, productId);
			pstmt.setInt(3, quantity);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		// 扣抵庫存
		sql = "update product set qty = qty - ? where product_id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, quantity);
			pstmt.setInt(2, productId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
