package repository;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import repository.entity.User;
import repository.entity.UserRepository;
import util.DBUtil;

public class UserRepositoryTest {

	@Test
	public void getAllUserTest() throws SQLException {
		UserRepository userRepository = new UserRepository();
		List<User> users= userRepository.getUsers();
		
		Assertions.assertFalse(users.isEmpty());
		
		DBUtil.close();
	}
}
