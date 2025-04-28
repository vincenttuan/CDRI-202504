package cart.service;

import java.util.List;

import cart.model.dto.UserDTO;

public interface UserListService {
	// 查詢所有使用者
	List<UserDTO> findAllUsers();
}
