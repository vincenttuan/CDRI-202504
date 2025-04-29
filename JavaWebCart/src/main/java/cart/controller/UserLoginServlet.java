package cart.controller;

import java.io.IOException;

import cart.model.dto.UserDTO;
import cart.service.UserLoginService;
import cart.service.impl.UserLoginServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {
	
	private UserLoginService userLoginService = new UserLoginServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/view/cart/user_login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String authcode = req.getParameter("authcode");
		// 取得目前存放在 session 的驗證碼(驗證碼圖片內容)
		String sessionAuthcode = session.getAttribute("authcode") + "";
		
		String resultMessage = null;
		try {
			UserDTO userDTO = userLoginService.login(username, password, authcode, sessionAuthcode);
			resultMessage = username + " 登入成功!";
			// 當登入資訊存入到 session 中
			session.setAttribute("userDTO", userDTO);
		} catch (RuntimeException e) {
			session.removeAttribute("userDTO"); // 移除舊有的登入資訊
			resultMessage = e.getMessage();
		}
		
		// 給 result.jsp 的資訊
		req.setAttribute("resultTitle", "登入結果");
		req.setAttribute("resultMessage", resultMessage);
		
		// 重導到 result.jsp
		req.getRequestDispatcher("/WEB-INF/view/cart/result.jsp").forward(req, resp);
	}
	
	
	
}

