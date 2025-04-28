<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<style>
	.item-count {
		background-color: red;
		color: white;
		border-radius: 50%; /* 圓形 */
		display: inline-block; /* 為了使用 padding 與圓形效果 */
		width: 20px;
		height: 20px;
		line-height: 20px; /* 垂直置中 */
		text-align: center; /* 水平置中 */
		font-size: 14px;
		font-weight: bold; /* 文字加粗 */
	}
</style>    
<div style="background: #DDDDDD;padding: 20px">
	${ username }&nbsp;&nbsp;&nbsp;&nbsp;
	👨‍👨‍👧‍👧<a href="/JavaWebCart/user/list">使用者列表</a> | 
	
	📄<a href="/JavaWebCart/product/list">商品管理</a> | 
	📝<a href="/JavaWebCart/product/order">商品訂購</a> | 
	<span class="item-count">${ itemCount }</span> 🛒<a href="/JavaWebCart/product/cart">商品購物車</a> | 
	📃<a href="/JavaWebCart/product/order/history">訂單歷史紀錄</a> |
	📊<a href="/JavaWebCart/product/statistics">商品統計</a> |
	 
	📴<a href="/JavaWebCart/user/logout">使用者登出</a> |
	🎅<a href="/JavaWebCart/user/register">使用者註冊</a> |
	👦<a href="/JavaWebCart/user/login">使用者登入</a> |
	
</div>