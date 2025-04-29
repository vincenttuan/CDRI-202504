package cart.model.dto;

import lombok.Data;

@Data
public class ProductDTO {
	// 對應 entity.Product 欄位資料
	private Integer productId;
	private String productName;
	private Integer price;
	private Integer qty;
	private String imageBase64;
	// 自訂欄位 total (庫存成本: price * qty)
	private Integer total;
}
