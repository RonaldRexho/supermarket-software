package service;

import java.util.List;

import javax.management.RuntimeErrorException;

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
	public void insert(User user) {
		if (userRepository.exist(user.getUsername())) {
			throw new RuntimeException("Username already taken");
		}
		userRepository.insert(user);
	}

	// update user method
	public void update(User user) {
		if (!userRepository.exist(user.getUsername())) {
			throw new RuntimeException("User does not exist");
		}
		userRepository.update(user);
	}

	// delete user

	public void delete(String username) {
		if (!userRepository.exist(username)) {
			throw new RuntimeException("User does not exist");
		} else {

			userRepository.delete(username);
		}
	}

}
