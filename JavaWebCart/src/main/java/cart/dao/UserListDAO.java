package cart.dao;

import java.util.List;

import cart.model.entity.User;

public interface UserListDAO {
	// 查詢所有使用者
	List<User> findAllUsers();
}
