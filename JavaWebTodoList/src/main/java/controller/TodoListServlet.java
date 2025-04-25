package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/todolist/*")
public class TodoListServlet extends HttpServlet  {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		resp.getWriter().print("pathInfo = " + pathInfo);
		switch (pathInfo) {
			case "/": // 顯示 Todo List 首頁
				
				break;
			case "/update": // 修改 Todo 紀錄
				String id = req.getParameter("id");
				String completed = req.getParameter("checked");
				String text = req.getParameter("text");
				
				if(completed != null) {
					// 修改 Todo 完成狀態
					
				} else if (text != null) {
					// 修改 Todo 內容
					
				}
				
				break;
			case "/delete": // 刪除某一項 Todo 紀錄
				
				break;
			default:  // 錯誤路徑
				resp.getWriter().print("path error");
				return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(!pathInfo.equals("/add")) {
			// 錯誤路徑
			resp.getWriter().print("path error");
			return;
		}
		// 進行新增程序
		
	}
	
	
	
	
}
