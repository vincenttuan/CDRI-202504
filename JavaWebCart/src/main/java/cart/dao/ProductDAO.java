package cart.dao;

import java.util.List;

import cart.model.entity.Product;

public interface ProductDAO {
	List<Product> findAllProducts(); // 查詢全部
	void add(Product product); // 新增
	void delete(Integer productId); // 刪除, 根據指定 productId
	
}
