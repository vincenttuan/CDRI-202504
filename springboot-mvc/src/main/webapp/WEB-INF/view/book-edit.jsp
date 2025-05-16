<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Edit Book</title>
	</head>
	<body>
		<%@ include file="include/menu.jsp" %>
		<div>
			<form method="post" action="/ssr/book/edit/${ book.id }">
				<input type="hidden" name="_method" value="PUT"/>
				書名: <input type="text" name="name" value="${ book.name }" required /><br />
				價格: <input type="number" name="price" value="${ book.price }" step="0.1" required /><br />
				數量: <input type="number" name="amount" value="${ book.amount }" required /><br />
				出刊: <input type="checkbox" name="pub" ${ book.pub ? "checked" : "" } /><br />
				<button type="submit">修改</button>
			</form>
		</div>
	</body>
</html>