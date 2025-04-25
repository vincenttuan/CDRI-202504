package service;

import java.util.ArrayList;
import java.util.List;

import dao.TodoListDao;
import dao.TodoListDaoImpl;
import model.dto.TodoDTO;
import model.entity.Todo;

public class TodoListServiceImpl implements TodoListService {
	
	private TodoListDao dao = new TodoListDaoImpl();
	
	/*
	@Override
	public List<TodoDTO> findAllTodos() {
		List<Todo> todos = dao.findAllTodos();
		// List<Todo> 轉 List<TodoDTO>
		List<TodoDTO> todoDTOs = new ArrayList<>();
		for(Todo todo : todos) {
			todoDTOs.add(transferToDTO(todo));
		}
		return todoDTOs;
	}
	*/
	@Override
	public List<TodoDTO> findAllTodos() {
		return dao.findAllTodos()
				  .stream()
				  .map(this::transferToDTO)
				  //.map(todo -> transferToDTO(todo))
				  .toList();
	}

	@Override
	public TodoDTO getTodo(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTodo(String text, Boolean completed) {
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
	
	// 轉換成 DTO
	private TodoDTO transferToDTO(Todo todo) {
		return new TodoDTO(todo.getId(), todo.getText(), todo.getCompleted());
	}
	
	// 轉換成 Entity
	private Todo transferToEntity(TodoDTO todoDTO) {
		return new Todo(todoDTO.getId(), todoDTO.getText(), todoDTO.getCompleted());
	}

}
