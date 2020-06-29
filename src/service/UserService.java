package service;

import java.util.List;

import model.User;
import repository.UserRepository;

public class UserService {

	private UserRepository userRepository;

	public UserService() {
		userRepository = new UserRepository();
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
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
