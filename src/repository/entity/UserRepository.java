package repository.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.DBUtil;

public class UserRepository {

	private static final String SELECT_ALL = "Select * from \"user\" inner join role on \"user\".role_id=role.id;";
	
public List<User> getUsers(){
	
	try (Statement st = DBUtil.connect().createStatement(); 
			ResultSet rs = st.executeQuery(SELECT_ALL)) {
		return extractUsersFromResultSet(rs);
		
		
	} catch (SQLException e) {
		
		System.out.println(e.getStackTrace());
	}
	return Collections.emptyList();
	
}

private List<User> extractUsersFromResultSet(ResultSet rs) throws SQLException {

	List<User> users = new ArrayList();	
	
	while (rs.next()) {
		
		User user = new User();
		user.setId(rs.getInt("id"));//edhe per te tjerat
		
//		rs.getDate("birthday").toLocalDate()
		 users.add(user);
	}
	
	return users;
}
	
	
	
}
