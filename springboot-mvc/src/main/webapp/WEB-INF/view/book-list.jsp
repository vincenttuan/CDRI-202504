<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Book List</title>
		<!-- DataTables CSS -->
		<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css"/>
		
		<!-- jQuery（必要）-->
		<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
		
		<!-- DataTables JS -->
		<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
		
		<!-- Buttons 插件 -->
		<link rel="stylesheet" href="https://cdn.datatables.net/buttons/2.4.1/css/buttons.dataTables.min.css"/>
		<script src="https://cdn.datatables.net/buttons/2.4.1/js/dataTables.buttons.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/2.4.1/js/buttons.html5.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/2.4.1/js/buttons.print.min.js"></script>
		
		<!-- PDF & Excel 匯出支援 -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.68/pdfmake.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.68/vfs_fonts.js"></script>
	</head>
	<body>
		<%@ include file="include/menu.jsp" %>
		<div>
			<form method="post" action="/ssr/book/add">
				書名: <input type="text" name="name" required /><br />
				價格: <input type="number" name="price" step="0.1" required /><br />
				數量: <input type="number" name="amount" required /><br />
				出刊: <input type="checkbox" name="pub" /><br />
				<button type="submit">送出</button>
			</form>
		</div>
		<div>
			<table border="1" id="bookTable">
				<thead>
					<tr>
						<th>ID</th><th>書名</th><th>價格</th><th>數量</th><th>出刊</th><th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="book" items="${ books }">
						<tr>
							<td>${ book.id }</td>
							<td>${ book.name }</td>
							<td>${ book.price }</td>
							<td>${ book.amount }</td>
							<td>${ book.pub }</td>
							<td>
								<a href="/ssr/book/edit/${ book.id }">修改</a> 
								&nbsp;|&nbsp; 
								<a href="/ssr/book/delete/${ book.id }">刪除</a>
								&nbsp;|&nbsp;
								<form style="display:inline" method="post" action="/ssr/book/delete/${ book.id }">
									<input type="hidden" name="_method" value="DELETE"/>
									<button type="submit">刪除</button>
								</form>
								&nbsp;|&nbsp;
								<a href="#" onclick="deleteBook(${ book.id })">刪除</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<script>
			async function deleteBook(id) {
				if(confirm('確定要刪除嗎?')) {
					const response = await fetch('/ssr/book/delete/' + id, {method:'DELETE'});
					if(response.redirected) {
						window.location.href=response.url;
					}
				}
			}
		</script>
		
		<!-- DataTables 初始化 -->
		<script>
			$(document).ready(function() {
				$('#bookTable').DataTable({
					// Bfrtpi [Buttons (匯出 Excel, PDF 等...), f(搜尋框), r(處理中的訊息), t(表格本體), i(顯示第n到m筆), p(分頁按鈕)]
					dom: 'Bfrtpi', // 顯示按鈕區塊
			        buttons: [
			            {
			                extend: 'excelHtml5',
			                text: '匯出 Excel',
			                exportOptions: {
			                    columns: [0, 1, 2, 3] // 匯出 ID、書名、價格、數量
			                }
			            },
			            {
			                extend: 'pdfHtml5',
			                text: '匯出 PDF',
			                exportOptions: {
			                    columns: [0, 1, 2, 3] // 匯出 ID、書名、價格、數量
			                },
			                orientation: 'landscape', // 橫向（可選）
			                pageSize: 'A4'
			            },
			            {
			                extend: 'print',
			                text: '列印'
			            }
			        ],
					language: {
						url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/zh-HANT.json'
					},
					
				});
			});
		</script>
		
	</body>
</html>