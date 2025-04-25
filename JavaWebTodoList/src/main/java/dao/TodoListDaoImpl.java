package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.Todo;

public class TodoListDaoImpl extends BaseDao implements TodoListDao {

	@Override
	public List<Todo> findAllTodos() {
		List<Todo> todos = new ArrayList<>();
		
		String sql = "select id, text, completed from todo order by id";
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
			// 逐一尋訪 rs 中的每一筆紀錄
			// 再將每一筆紀錄轉成 Todo 物件
			// 最後加入到 todos 集合中
			while(rs.next()) {
				Todo todo = new Todo();
				todo.setId(rs.getInt("id"));
				todo.setText(rs.getString("text"));
				todo.setCompleted(rs.getBoolean("completed"));
				
				todos.add(todo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
 		
		return todos;
	}

	@Override
	public Todo getTodo(Integer id) {
		String sql = "select id, text, completed from todo where id=?";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			// 得到 rs 中的紀錄(1或0筆)
			// 再將該筆紀錄轉成 Todo 物件並回傳
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					Todo todo = new Todo();
					todo.setId(rs.getInt("id"));
					todo.setText(rs.getString("text"));
					todo.setCompleted(rs.getBoolean("completed"));
					
					return todo;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addTodo(Todo todo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTodoComplete(Integer id, Boolean completed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTodoText(Integer id, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTodo(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
