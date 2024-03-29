package repository;

import static util.DBUtil.createStatement;
import static util.DBUtil.prepareStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserRepository {

	private static final String SELECT_ALL = "SELECT * FROM \"user\" ORDER BY first_name, last_name;";
	private static final String EXIST = "SELECT COUNT(*) FROM \"user\" WHERE username=?";
	private static final String INSERT = "INSERT INTO \"user\"(	first_name, last_name, username, email, password, phone, birthday, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String FIND_BY_USERNAME = "SELECT * FROM \"user\" WHERE username=?";
	private static final String UPDATE = "UPDATE \"user\" SET first_name=?, last_name=?, email=?, phone=?, role_id=?, birthday=? ${password} WHERE username=?";
	private static final String DELETE = "DELETE FROM \"user\" WHERE username=?;";

	public List<User> getUsers() throws SQLException {
		try (Statement st = createStatement()) {
			ResultSet rs = st.executeQuery(SELECT_ALL);
			return extractUsersFromResultSet(rs);
		}
	}

	public void insert(User user) throws SQLException {
		try (PreparedStatement st = prepareStatement(INSERT)) {
			st.setString(1, user.getFirstName());
			st.setString(2, user.getLastName());
			st.setString(3, user.getUsername());
			st.setString(4, user.getEmail());
			st.setString(5, String.valueOf(user.getPassword()));
			st.setString(6, user.getPhone());
			st.setDate(7, user.getBirthday());
			st.setInt(8, user.getRoleId());
			st.executeUpdate();
		}
	}

	public boolean exist(String username) throws SQLException {
		try (PreparedStatement st = prepareStatement(EXIST)) {
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			rs.next();
			long count = rs.getLong("count");
			return count > 0;
		}
	}

	public User findByUsername(String username) throws SQLException {
		try (PreparedStatement st = prepareStatement(FIND_BY_USERNAME)) {
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			rs.next();
			return extractUser(rs);
		}
	}

	public void update(User user) throws SQLException {
		boolean isPasswordUpdate = user.getPassword().length > 0;
		String sqlUpdate = UPDATE;
		if (isPasswordUpdate) {
			sqlUpdate = UPDATE.replace("${password}", ", password=?");
		} else {
			sqlUpdate = UPDATE.replace("${password}", "");
		}
		
		try (PreparedStatement st = prepareStatement(sqlUpdate)) {
			st.setString(1, user.getFirstName());
			st.setString(2, user.getLastName());
			st.setString(3, user.getEmail());
			st.setString(4, user.getPhone());
			st.setInt(5, user.getRoleId());
			st.setDate(6, user.getBirthday());
			if (isPasswordUpdate) {
				st.setString(7, String.valueOf(user.getPassword()));
				st.setString(8, user.getUsername());
			} else {
				st.setString(7, user.getUsername());
			}
			st.executeUpdate();
		}
	}

	public void delete(String username) throws SQLException {
		try (PreparedStatement st = prepareStatement(DELETE)) {
			st.setString(1, username);
			st.executeUpdate();
		}
	}

	private List<User> extractUsersFromResultSet(ResultSet rs) throws SQLException {
		List<User> users = new ArrayList<>();
		while (rs.next()) {
			User user = extractUser(rs);
			users.add(user);
		}
		return users;
	}

	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setEmail(rs.getString("email"));
		user.setUsername(rs.getString("username"));
		String password = rs.getString("password");
		user.setPassword(password.toCharArray());
		user.setPhone(rs.getString("phone"));
		user.setBirthday(rs.getDate("birthday"));
		user.setRoleId(rs.getInt("role_id"));
		return user;
	}

}
