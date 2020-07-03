package repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Role;
import model.User;
import util.DBUtil;

public class UserRepository {

	private static final String SELECT_ALL = "SELECT * FROM \"user\";";
	private static final String EXIST = "SELECT COUNT(*) FROM \"user\" WHERE username=?";
	private static final String INSERT = "INSERT INTO \"user\"(	 first_name, last_name, username, email, password, phone) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String FIND_BY_ID = "SELECT * FROM \"user\" WHERE id=?";
	private static final String FIND_BY_USERNAME = "SELECT * FROM \"user\" WHERE username=?";
	private static final String UPDATE = "UPDATE \"user\" SET first_name=?, last_name=?, email=?, phone=?, password=? WHERE username=?";
	private static final String DELETE = "DELETE FROM \"user\" WHERE username=?;";

	public List<User> getUsers() {

		try (Statement st = DBUtil.connect().createStatement(); ResultSet rs = st.executeQuery(SELECT_ALL)) {
			return extractUsersFromResultSet(rs);

		} catch (SQLException e) {

			System.out.println(e.getStackTrace());
		}
		return Collections.emptyList();

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
		user.setPassword(rs.getString("password"));
		user.setPhone(rs.getString("phone"));
		user.setBirthday(rs.getDate("birthday"));
		user.setSalary(rs.getDouble("salary"));
		user.setActive(rs.getBoolean("active"));
		user.setRole(Role.getRole(rs.getInt("role_id")));
		return user;
	}

	public void insert(User user) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			st.setString(1, user.getFirstName());
			st.setString(2, user.getLastName());
			st.setString(3, user.getUsername());
			st.setString(4, user.getEmail());
			st.setString(5, user.getPassword());
			st.setString(6, user.getPhone());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean exist(String username) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(EXIST)) {

			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			rs.next();
			long count = rs.getLong("count");

			if (count > 0) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// find user by id
	public User findByUsername(String username) {

		User user = null;

		try (PreparedStatement st = DBUtil.connect().prepareStatement(FIND_BY_USERNAME)) {

			st.setString(1, username);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				user = extractUser(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}
	
	// find user by username
	public User findById(int id) {

		User user = null;

		try (PreparedStatement st = DBUtil.connect().prepareStatement(FIND_BY_ID)) {

			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				user = extractUser(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	// update

	public void update(User user) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(UPDATE)) {
			st.setString(1, user.getFirstName());
			st.setString(2, user.getLastName());
			st.setString(3, user.getEmail());
			st.setString(4, user.getPhone());
			st.setString(5, user.getPassword());
			st.setString(6, user.getUsername());

			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	// delete

	public void delete(String username) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(DELETE)) {

			st.setString(1, username);
			st.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
