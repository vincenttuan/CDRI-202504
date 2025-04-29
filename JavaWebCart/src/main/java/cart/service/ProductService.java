package cart.service;

import java.util.List;

import cart.model.entity.Product;

public interface ProductService {
	List<Product> findAllProducts(); // 查詢全部
	void add(String productName, String price, String qty, String productImageBase64); // 新增
	void delete(Integer productId); // 刪除, 根據指定 productId

}
