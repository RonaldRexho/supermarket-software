package service;

import java.sql.SQLException;
import java.util.List;

import exception.SupermarketException;
import model.User;
import repository.UserRepository;


public class UserService {

	private UserRepository userRepository;

	public UserService() {
		userRepository = new UserRepository();
	}

	public User findByUsername(String username) {
		try {
			if (userRepository.exist(username)) {
				return userRepository.findByUsername(username);
			} else {
				throw new SupermarketException("Invalid username");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}
	}

	public List<User> getUsers() {
		try {
			return userRepository.getUsers();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}
	}

	public void insert(User user) {
		try {

			if (userRepository.exist(user.getUsername())) {
				throw new SupermarketException("Username already taken");
			} else {
				userRepository.insert(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}

	}

	public void update(User user) {
		try {

			if (!userRepository.exist(user.getUsername())) {
				throw new SupermarketException("User does not exist");
			} else {
				userRepository.update(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}

	}

	public void delete(String username) {
		try {

			if (!userRepository.exist(username)) {
				throw new RuntimeException("User does not exist");
			} else {
				userRepository.delete(username);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}

	}

}
