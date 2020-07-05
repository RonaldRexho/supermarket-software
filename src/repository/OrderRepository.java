package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Order;
import model.OrderItem;
import util.DBUtil;

public class OrderRepository {

	private final String INSERT = "INSERT INTO \"order\" (code) VALUES (?);";
	private final String INSERT_ORDER_ITEM = "INSERT INTO order_item(price, quantity, order_id, product_id) VALUES (?, ?, ?, ?);"; 

	public int insert(Order order) {
		try (PreparedStatement st = DBUtil.connect().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			st.setLong(1, order.getCode());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			return rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error");
		}

	}
	public void insertOrderItem (OrderItem orderItem) {
		try (PreparedStatement st = DBUtil.connect().prepareStatement(INSERT_ORDER_ITEM)){
			st.setDouble(1, orderItem.getPrice());
			st.setInt(2, orderItem.getQuantity());
			st.setInt(3, orderItem.getOrderId());
			st.setInt(4, orderItem.getProductId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Error");
		}
	}

}
