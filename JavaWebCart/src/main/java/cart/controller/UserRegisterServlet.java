package cart.controller;

import java.io.IOException;

import cart.service.EmailService;
import cart.service.UserRegisterService;
import cart.service.impl.UserRegisterServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user/register")
public class UserRegisterServlet extends HttpServlet {
	
	private UserRegisterService userRegisterService = new UserRegisterServiceImpl();
	
	private EmailService emailService = new EmailService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/view/cart/user_register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收表單資料
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		
		// 新增使用者
		userRegisterService.addUser(username, password, email);
		
		// 發送 email
		String emailConfirmLink = "http://localhost:8080/JavaWebCart/email/confirm?username=" + username;
		emailService.sendEmail(email, emailConfirmLink);
		
		// 準備要給 result.jsp 的資訊
		String resultTitle = "註冊結果";
		String resultMessage = "用戶 " + username + " 註冊成功 !";
		resultMessage += "<p />";
		resultMessage += "系統已送出驗證信件到 " + email + " 信箱, 請收信並點選[確認]連結";
		
		req.setAttribute("resultTitle", resultTitle);
		req.setAttribute("resultMessage", resultMessage);
		
		// 重導到 result.jsp
		req.getRequestDispatcher("/WEB-INF/view/cart/result.jsp").forward(req, resp);
	}
	
	
}
