package cart.controller;

import java.io.IOException;

import cart.service.UserRegisterService;
import cart.service.impl.UserRegisterServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 要接收使用者於信件中所按下的確認連結
// http://localhost:8080/JavaWebCart/email/confirm?username=John
// http://localhost:8080/JavaWebCart/email/confirm?username=Mary

@WebServlet("/email/confirm")
public class EmailConfirmServlet extends HttpServlet {
	
	private UserRegisterService userRegisterService = new UserRegisterServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 得到 username
		String username = req.getParameter("username");
		
		// 驗證 email (修改 user 資料表中 completed 欄位的資訊) 重要 !!
		userRegisterService.emailConfirmOK(username);
		
		// 準備要給 result.jsp 的資訊
		String resultTitle = "Email驗證結果";
		String resultMessage = "用戶名稱: " + username + "<p />Email驗證成功";
		req.setAttribute("resultTitle", resultTitle);
		req.setAttribute("resultMessage", resultMessage);
		
		// 重導到 result.jsp
		req.getRequestDispatcher("/WEB-INF/view/cart/result.jsp").forward(req, resp);
	}
	
}
