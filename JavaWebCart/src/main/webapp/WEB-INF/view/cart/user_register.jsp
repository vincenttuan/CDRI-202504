<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>會員註冊</title>
	<link rel="stylesheet"
		href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<style>
			.content {
				display: flex;
				justify-content: center; /* 水平居中 */
				align-items: center; /* 垂直居中 */
				height: 50vh; /* 視窗高度 */
				margin: 0; /* 移除預設邊距 */
				padding: 15px;
				background-color: #f0f0f0; /* 背景顏色，可選 */
				border: 1px solid #ccc; /* 邊框，可選 */
			}
		</style>
</head>
<body>
	<!-- menu bar -->
	<%@ include file="/WEB-INF/view/cart/menu.jsp"%>
	<!-- content -->
	<div class="content">
		<form class="pure-form" method="POST" action="/JavaWebCart/user/register">
			<fieldset>
				<legend>會員註冊</legend>
				帳號: <input type="text" name="username" placeholder="請輸入帳號" required="required" /><p>
				密碼: <input type="text" name="password" placeholder="請輸入密碼" required="required" /><p>
				電郵: <input type="email" name="email" placeholder="請輸入電子郵件" required="required" /><p>
				<button type="reset" class="pure-button">清空</button>
				<button type="submit" class="pure-button pure-button-primary">傳送</button>
			</fieldset>
		</form>
	</div>
</body>
</html>