<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>商品訂單列表管理</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<style>
			#preview {
				margin-top: 10px;
				max-width: 200px;
				max-height: 200px;
				border: 1px solid #ddd;
				padding: 5px;
			}
			.products-container {
				display: flex;
				flex-wrap: wrap;
				gap: 10px; /* 商品之間的間距 */
			}
			.product-name {
				font-size: 20px;
				font-weight: blod;
				color: #000000;
			}
			.product-price {
				font-size: 35px;
				font-weight: blod;
				color: #D9006C;
			}
		</style>
	</head>
	<body>
		<!-- menu bar -->
		<%@ include file="/WEB-INF/view/cart/menu.jsp"  %>
		<!-- content -->
		<div style="padding: 15px" class="pure-form">
			<fieldset>
				<legend>商品訂單列表</legend>
					<div class="products-container">
						<c:forEach var="productDTO" items="${ productDTOs }">
							<div class="product-item" onmouseover="this.style.backgroundColor='#E0E0ff'" 
							    onmouseout="this.style.backgroundColor=''">
								<img width="200" src='data:image/png;base64,${ productDTO.imageBase64 }'>
								<br>
								<p class="product-name">${ productDTO.productName }(No.${ productDTO.productId })<p>
								<p class="product-price">$${ productDTO.price }<p />
								庫存數量:${ productDTO.qty }<p />
								<a class="pure-button pure-button-primary" href="/JavaWebCart/product/order/add/cart?productId=${ productDTO.productId }">加入購物車</a>
							</div>
						</c:forEach>	
					</div>
				</table>
			</fieldset>
	
		</div>
		
	</body>
</html>