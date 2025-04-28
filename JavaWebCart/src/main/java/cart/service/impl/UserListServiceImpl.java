package cart.service.impl;

import java.util.List;

import cart.dao.UserListDAO;
import cart.dao.impl.UserListDAOImpl;
import cart.model.dto.UserDTO;
import cart.model.entity.User;
import cart.service.UserListService;

public class UserListServiceImpl implements UserListService {
	private UserListDAO userListDAO = new UserListDAOImpl();
	@Override
	public List<UserDTO> findAllUsers() {
		// 1.從 userListDAO 取得 List<User>
		List<User> users = userListDAO.findAllUsers();
		
		// 2.將 List<User> 每一個元素由 User 轉成 UserDTO 使之變成 List<UserDTO>
		List<UserDTO> userDTOs = users.stream()
									  .map(user -> {
										  UserDTO userDTO = new UserDTO();
										  userDTO.setId(user.getId());
										  userDTO.setUsername(user.getUsername());
										  userDTO.setEmail(user.getEmail());
										  userDTO.setCompleted(user.getCompleted());
										  return userDTO;
									  })
									  .toList();
		
		return userDTOs;
	}
	
}
