<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Spring Form 表單標籤 -->
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Room Update</title>
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
					<!-- Room 表單修改 -->
					<td valign="top">
						<sp:form class="pure-form" method="post" modelAttribute="roomDto" action="/room/update/${ roomDto.roomId }" >
							<fieldset>
								<legend>Room 表單修改</legend>
								Room 房號: <sp:input type="number" path="roomId" readonly="true" />
								<p />
								Room 名稱: <sp:input type="text" path="roomName" />
								<sp:errors path="roomName" style="color: red" />
								<p />
								Room 人數: <sp:input type="number" path="roomSize" />
								<sp:errors path="roomSize" style="color: red" />
								<p />
								<button type="submit" class="pure-button pure-button-primary">修改</button>
							</fieldset>
							
						</sp:form>
						
					</td>
				</tr>
			</table>
			
		</div>
	</body>
</html>