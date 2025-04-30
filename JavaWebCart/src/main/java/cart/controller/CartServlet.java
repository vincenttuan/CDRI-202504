package cart.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/product/cart")
public class CartServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		if(session.getAttribute("cart") == null) {
			req.setAttribute("resultTitle", "購物車");
			req.setAttribute("resultMessage", "購物車無任何商品");
			req.getRequestDispatcher("/WEB-INF/view/cart/result.jsp").forward(req, resp);
			return;
		}
		
		// 重導到 cart.jsp
		req.getRequestDispatcher("/WEB-INF/view/cart/cart.jsp").forward(req, resp);
	}
	
}
