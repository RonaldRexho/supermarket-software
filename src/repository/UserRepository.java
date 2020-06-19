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

import repository.entity.Role;
import repository.entity.User;
import util.DBUtil;

public class UserRepository {

	private static final String SELECT_ALL = "SELECT * FROM \"user\";";
	private static final String EXIST = "SELECT COUNT(*) FROM \"user\" WHERE username=?";
	private static final String INSERT = "INSERT INTO \"user\"(\r\n"
			+ "	 first_name, last_name, birthday, username, email, password, phone, salary)"
			+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String FIND_BY_ID = "SELECT * FROM \"user\" WHERE id=?";
	private static final String UPDATE = "UPDATE public.\"user\""
			+ "	SET first_name=?, last_name=?, birthday=?, email=?, phone=?, salary=?, modified_on=?\r\n"
			+ "	WHERE id=?";
	private static final String DELETE = "UPDATE public.\"user\"" + "	SET active=false" + "	WHERE id=?;";

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
		user.setPhone(rs.getString("phone"));
		user.setBirthday(rs.getDate("birthday"));
		user.setSalary(rs.getDouble("salary"));
		user.setActive(rs.getBoolean("active"));
		user.setRole(Role.getRole(rs.getInt("role_id")));
		return user;
	}

	public User insert(User user) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			st.setString(1, user.getFirstName());
			st.setString(2, user.getLastName());
			st.setDate(3, user.getBirthday());
			st.setString(4, user.getUsername());
			st.setString(5, user.getEmail());
			st.setString(6, user.getPassword());
			st.setString(7, user.getPhone());
			st.setDouble(8, user.getSalary());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			user.setId(rs.getInt("id"));
			user.setRole(Role.getRole(rs.getInt("role_id")));
			user.setActive(rs.getBoolean("active"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
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

	public User update(User user) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(UPDATE)) {
			st.setString(1, user.getFirstName());
			st.setString(2, user.getLastName());
			st.setDate(3, user.getBirthday());
			st.setString(4, user.getEmail());
			st.setString(5, user.getPhone());
			st.setDouble(6, user.getSalary());
			st.setDate(7, new Date(LocalDate.now().toEpochDay()));
			st.setInt(8, user.getId());

			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;

	}

	// delete

	public boolean delete(int id) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(DELETE)) {

			st.setInt(1, id);
			int affectedRows = st.executeUpdate();
			if (affectedRows > 0) {
				return true;
			}
			return false;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;

	}

}
