package cart.controller;

import java.io.IOException;
import java.util.List;

import cart.model.dto.ProductDTO;
import cart.model.dto.UserDTO;
import cart.service.OrderService;
import cart.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 購物車結帳
@WebServlet("/product/cart/submit")
public class CartSubmitServlet extends HttpServlet {
	
	private OrderService orderService = new OrderServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 從 session 中取得 userDTO 與 cart 資訊
		HttpSession session = req.getSession();
		UserDTO userDTO = (UserDTO)session.getAttribute("userDTO");
		List<ProductDTO> cart = (List<ProductDTO>)session.getAttribute("cart");
		
		// 新增訂單到資料表
		orderService.addOrder(userDTO.getId(), cart);
		
		// 清空購物車
		session.setAttribute("cart", null);
		//session.removeAttribute("cart");
		
		// 重導到 result.jsp
		req.setAttribute("resultTitle", "購物車-結帳");
		req.setAttribute("resultMessage", "購物車結帳完畢");
		req.getRequestDispatcher("/WEB-INF/view/cart/result.jsp").forward(req, resp);
	}
}
