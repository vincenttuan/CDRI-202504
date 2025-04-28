package cart.dao;

import cart.model.entity.User;

public interface UserRegisterDAO {
	// 新增 User
	int addUser(User user);
	
	// email 驗證成功並修改 completed = true
	int emailConfirmOK(String username);
}
