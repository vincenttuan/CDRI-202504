package cart.model.entity;

import lombok.Data;

@Data
public class User {
	private Integer id;
	private String username;
	private String hashPassword;
	private String hashSalt;
	private String email;
	private Boolean completed;
}
