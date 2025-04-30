package cart.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cart.dao.ProductDAO;
import cart.model.entity.Product;

public class ProductDAOImpl extends BaseDao implements ProductDAO {

	@Override
	public void add(Product product) {
		String sql = "insert into product(product_name, price, qty, image_base64) values(?, ?, ?, ?)";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 配置資料到 ?
			pstmt.setString(1, product.getProductName());
			pstmt.setInt(2, product.getPrice());
			pstmt.setInt(3, product.getQty());
			pstmt.setString(4, product.getImageBase64());
			
			// 執行新增
			int rowcount = pstmt.executeUpdate();
			System.out.println("新增成功筆數:" + rowcount);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Product> findAllProducts() {
		String sql = "select product_id, product_name, price, qty, image_base64 from product";
		List<Product> products = new ArrayList<>();
		
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
			// 逐筆取得資料紀錄
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setPrice(rs.getInt("price"));
				product.setQty(rs.getInt("qty"));
				product.setImageBase64(rs.getString("image_base64"));
				// 將 product 注入到 products 集合中
				products.add(product);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public void delete(Integer productId) {
		String sql = "delete from product where product_id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, productId);
			int rowcount = pstmt.executeUpdate();
			System.out.println("資料刪除筆數:" + rowcount);
		} catch(SQLException e) {
			e.printStackTrace();
		}	
	}
}

