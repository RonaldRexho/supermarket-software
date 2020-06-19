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

}
