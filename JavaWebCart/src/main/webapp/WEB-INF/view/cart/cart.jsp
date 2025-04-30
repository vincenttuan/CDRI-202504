<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>購物車</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
    <style>
        #preview {
            margin-top: 10px;
            max-width: 200px;
            max-height: 200px;
            border: 1px solid #ddd;
            padding: 5px;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/view/cart/menu.jsp" %>

    <div style="padding: 15px" class="pure-form">
        <table>
            <td valign="top">
                <fieldset>
                    <legend>購物車</legend>
                    <table class="pure-table pure-table-bordered">
                        <thead>
                            <tr>
                                <th>刪除</th><th>Index</th><th>商品ID</th>
                                <th>商品名稱</th><th>商品價格</th><th>商品照片</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="total" value="0" />
                            <c:forEach var="item" items="${cart}" varStatus="status">
                                <c:set var="total" value="${total + item.price}" />
                                <tr onmouseover="this.style.backgroundColor='#E0E0ff'" 
                                    onmouseout="this.style.backgroundColor=''">
                                    <td>
                                        X
                                    </td>
                                    <td align="center">${status.index}</td>
                                    <td align="center">${item.productId}</td>
                                    <td>${item.productName}</td>
                                    <td align="right">${item.price}</td>
                                    <td><img width="100" src="data:image/png;base64,${item.imageBase64}" /></td>
                                </tr>
                            </c:forEach>
                            <!-- 總計 -->
                            <tr>
                                <td colspan="4" align="right">總計</td>
                                <td align="right">${total}</td>
                                <td align="center">
                                    <a class="pure-button pure-button-primary" href="/JavaWebCart/product/cart/submit">
                                        結帳
                                    </a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </fieldset>
            </td>
        </table>
    </div>
</body>
</html>