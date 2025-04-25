package controller;

import java.io.IOException;
import java.util.List;

import dao.ProductDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.OrderDTO;
import service.OrderService;
import service.ProductService;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	
	private OrderService orderService = new OrderService();
	private ProductService productService = new ProductService();
	
	// 查看歷史資料
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 得到歷史紀錄
		List<OrderDTO> orderDTOs = orderService.getOrderHistory();
		// 計算總金額
		int totalPrice = orderService.getTotalPrice();
		
		// 重導到指定 jsp 並帶上歷史紀錄資料
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/history.jsp");
		req.setAttribute("orderDTOs", orderDTOs);
		req.setAttribute("totalPrice", totalPrice);
		rd.forward(req, resp);
	}
	
	// 接收訂單的請求
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		// 取得使用者在表單中所訂購的商品
		String item = req.getParameter("item"); 
		// 新增訂單並會得到反饋
		OrderDTO orderDTO = orderService.addOrder(item);
		// 重導到指定 jsp 並帶上歷史反饋資料(OrderDTO)
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/result.jsp");
		req.setAttribute("orderDTO", orderDTO);
		rd.forward(req, resp);
	}
	
}
