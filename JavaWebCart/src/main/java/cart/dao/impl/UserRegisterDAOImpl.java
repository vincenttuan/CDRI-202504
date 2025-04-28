package cart.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import cart.dao.UserRegisterDAO;
import cart.model.entity.User;

public class UserRegisterDAOImpl extends BaseDao implements UserRegisterDAO {

	@Override
	public int addUser(User user) {
		String sql = "insert into user(username, hash_password, hash_salt, email) values(?, ?, ?, ?)";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getHashPassword());
			pstmt.setString(3, user.getHashSalt());
			pstmt.setString(4, user.getEmail());
			
			int rowcount = pstmt.executeUpdate(); // 執行更新
			return rowcount; // 若 rowcount = 1 表是新增一筆成功
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// email 驗證成功並修改 completed = true
	@Override
	public int emailConfirmOK(String username) {
		String sql = "update user set completed = true where username = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, username);
			
			int rowcount = pstmt.executeUpdate(); // 執行更新
			return rowcount; // 若 rowcount = 1 表是更新一筆成功
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
