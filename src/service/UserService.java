package service;

import java.util.List;

import repository.UserRepository;
import repository.entity.User;

public class UserService {

	private UserRepository userRepository;

	public UserService() {
		userRepository = new UserRepository();
	}

	public List<User> getUsers() {
		return userRepository.getUsers();
	}

	// control if username exist
	public User insert(User user) {

		if (userRepository.exist(user.getUsername())) {

			throw new RuntimeException("Username already taken");
		}

		return userRepository.insert(user);

	}

	// update user method
	public User update(User user) {

		if (userRepository.exist(user.getUsername())) {

			return userRepository.update(user);
		}
		return user;
	}
	
	
	// delete user
	
	public boolean delete(int id) {
		
		return userRepository.delete(id);
		
	}
	

}
