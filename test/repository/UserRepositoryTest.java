package repository;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import model.Role;
import model.User;
import util.DBUtil;

public class UserRepositoryTest {

	@Test
	public void getAllUserTest() throws SQLException {
		UserRepository userRepository = new UserRepository();
		List<User> users = userRepository.getUsers();

		Assertions.assertFalse(users.isEmpty());

		for (User user : users) {
			System.out.println(user.toString());
		}

		DBUtil.close();

	}

	@Test
	public void checkUserExistTest() throws SQLException {
		UserRepository userRepository = new UserRepository();

		String username = "admin";

		boolean result = userRepository.exist(username);
		Assertions.assertTrue(result);

		DBUtil.close();

	}

	@Test
	public void checkUserNotExist() throws SQLException {
		UserRepository userRepository = new UserRepository();

		String username = "gjon";
		boolean result = userRepository.exist(username);
		Assertions.assertFalse(result);

		DBUtil.close();

	}

	@Test
	public void insertUsersTest() {

		UserRepository userRepository = new UserRepository();

		User user = new User();
		user.setFirstName("emiljano");
		user.setLastName("admini");
		user.setUsername("administratoriiiii");
		user.setEmail("admin@gmail.cim");
		user.setPhone("938339939");
		user.setPassword("d883838");
		user.setSalary(993939.44);
		user.setBirthday(new Date(LocalDate.now().toEpochDay()));

		User createUser = userRepository.insert(user);

		Assertions.assertTrue(createUser.getId() > 0);
		Assertions.assertEquals(Role.CASHIER.getName(), createUser.getRole());
		Assertions.assertTrue(createUser.isActive());
	}
	
	@Test
	public void findByidTest() {
		
		UserRepository userRepository = new UserRepository();

	User user = userRepository.findById(-1);
	Assertions.assertNull(user);
		
	}
	
	@Test
	public void updateUserTest() {
		UserRepository userRepository = new UserRepository();

	User user = userRepository.findById(1);
	user.setFirstName("Goni");
	user.setLastName("Lake");
	user.setEmail("dafdfsdf");
    user.setPhone("343423423");
    user.setSalary(1111.33);
    
    userRepository.update(user);
    
	User updated = userRepository.findById(1);
	Assertions.assertEquals(updated.getFirstName(),"Goni");
	Assertions.assertEquals(updated.getLastName(),"Lake");
	Assertions.assertEquals(updated.getEmail(),"dafdfsdf");
	Assertions.assertEquals(updated.getPhone(),"343423423");
	Assertions.assertEquals(updated.getSalary(),1111.33);

    
    
	}
	
	
	@Test
	public void deleteTest() {
	
		UserRepository userRepository = new UserRepository();

		boolean result = userRepository.delete(1);
		
		Assertions.assertTrue(result);
		
		
	}
	

}
