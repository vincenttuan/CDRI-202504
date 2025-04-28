package cart.dao;

import cart.model.entity.User;

public interface UserRegisterDAO {
	// 新增 User
	int addUser(User user);
	
	// email 驗證
	int emailConfirmOK(String username);
}
