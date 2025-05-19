<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Room</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/css/buttons.css">
	</head>
	<body>
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu.jspf" %>
		
		<!-- body content -->
		<div style="padding: 15px">
			
			<table>
				<tr>
					<!-- 新增 Room 表單 -->
					<td valign="top">
						<%@ include file="/WEB-INF/view/room/room_form.jspf" %>
					</td>
					
					<!-- 列表 Rooms -->
					<td valign="top">
						<%@ include file="/WEB-INF/view/room/room_list.jspf" %>	
					</td>
				</tr>
			</table>
			
		</div>
	</body>
</html>