package cart.service.impl;

import cart.dao.UserLoginDAO;
import cart.dao.impl.UserLoginDaoImpl;
import cart.model.dto.UserDTO;
import cart.model.entity.User;
import cart.service.UserLoginService;
import cart.util.HashUtil;

public class UserLoginServiceImpl implements UserLoginService {
	
	private UserLoginDAO userLoginDAO = new UserLoginDaoImpl();
	
	@Override
	public UserDTO login(String username, String password, String authCode, String sessionAuthCode) {
		// 1. 比對驗證碼
		if(!authCode.equals(sessionAuthCode)) {
			throw new RuntimeException("驗證碼不符");
		}
		// 2. 比對有無使用者
		User user = userLoginDAO.findUserByName(username);
		if(user == null) {
			throw new RuntimeException("查無使用者");
		}
		// 3. email 是否驗證通過
		boolean completed = user.getCompleted();
		if(!completed) {
			throw new RuntimeException("Email 尚無驗證通過");
		}
		// 4. 驗證密碼
		try {
			String hashPassword = HashUtil.hashPassword(password, user.getHashSalt());
			boolean checkPassword = user.getHashPassword().equals(hashPassword);
			if(!checkPassword) {
				throw new RuntimeException("密碼錯誤");
			}
			// 5. 驗證成功
			// user 轉 userDTO
			UserDTO userDTO = new UserDTO();
			userDTO.setId(user.getId());
			userDTO.setUsername(user.getUsername());
			userDTO.setEmail(user.getEmail());
			userDTO.setCompleted(user.getCompleted());
			// 6. 將 userDTO 回傳
			return userDTO;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}

}
