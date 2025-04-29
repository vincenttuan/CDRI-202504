<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>使用者登入</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<style type="text/css">
			/* 正中央 */
			body {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
		</style>
	</head>
	<body style="padding: 15px">
		<form class="pure-form" method="post" action="/JavaWebCart/user/login">
			<fieldset>
				<legend>使用者登入</legend>
				<div class="pure-control-group" style="padding-bottom: 5px">
					<label for="username">💇‍♂️ 帳號</label>
					<input name="username" type="text" placeholder="請輸入帳號" required />
				</div>
				<div class="pure-control-group" style="padding-bottom: 5px">
					<label for="password">🔐 密碼</label>
					<input name="password" type="password" placeholder="請輸入密碼" required />
				</div>
				<div class="pure-control-group" style="padding-bottom: 5px">
					<label for="authcode">🔢 驗證</label>
					<input name="authcode" type="text" placeholder="請輸入驗證碼" required />
					<img src="/JavaWebCart/user/authcode" valign="middle" title="驗證碼" alt="驗證碼" />
				</div>
				<div class="pure-controls">
					<button type="submit" class="pure-button pure-button-primary">登入</button>
				</div>
			</fieldset>
			
		</form>
		
	</body>
</html>














