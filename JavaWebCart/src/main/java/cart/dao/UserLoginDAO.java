package cart.dao;

import cart.model.entity.User;

public interface UserLoginDAO {
	
	User findUserByName(String username);
	
}
