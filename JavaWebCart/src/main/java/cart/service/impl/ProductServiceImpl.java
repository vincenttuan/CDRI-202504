package cart.service.impl;

import java.util.List;

import cart.dao.ProductDAO;
import cart.dao.impl.ProductDAOImpl;
import cart.model.dto.ProductDTO;
import cart.model.entity.Product;
import cart.service.ProductService;

public class ProductServiceImpl implements ProductService {
	
	private ProductDAO productDAO = new ProductDAOImpl();
	
	@Override
	public void add(String productName, String price, String qty, String productImageBase64) {
		Product product = new Product();
		product.setProductName(productName);
		product.setPrice(Integer.parseInt(price));
		product.setQty(Integer.parseInt(qty));
		product.setImageBase64(productImageBase64);
		
		productDAO.add(product);
	}

	@Override
	public List<ProductDTO> findAllProducts() {
		return productDAO.findAllProducts() // List<Product>
						 .stream()  // ... Product
						 //.map(product -> mapToProductDTO(product)) // ... ProductDTO
						 .map(this::mapToProductDTO) // ... ProductDTO
						 .toList(); // List<ProductDTO>
	}
	
	// ProductDTO 轉 Product 物件的方法
	private Product mapToProduct(ProductDTO productDTO) {
		Product product = new Product();
		// 將 productDTO 的屬性內容逐一設定到 product 屬性中
		product.setProductId(productDTO.getProductId());
		product.setProductName(productDTO.getProductName());
		product.setPrice(productDTO.getPrice());
		product.setQty(productDTO.getQty());
		product.setImageBase64(productDTO.getImageBase64());
		return product;
	}
	
	// Product 轉 ProductDTO 物件的方法
	private ProductDTO mapToProductDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(product.getProductId());
		productDTO.setProductName(product.getProductName());
		productDTO.setPrice(product.getPrice());
		productDTO.setQty(product.getQty());
		productDTO.setImageBase64(product.getImageBase64());
		// 重要 !! (自訂欄位)
		Integer total = product.getPrice() * product.getQty();
		productDTO.setTotal(total);
		
		return productDTO;
	}

	@Override
	public void delete(Integer productId) {
		productDAO.delete(productId);
	}
	

}
