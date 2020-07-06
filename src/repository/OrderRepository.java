package repository;

import static util.DBUtil.prepareStatement;
import static util.DBUtil.prepareStatementAndGetGeneratedKeys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Order;
import model.OrderItem;

public class OrderRepository {

	private final String INSERT_ORDER = "INSERT INTO \"order\" (code) VALUES (?);";
	private final String INSERT_ORDER_ITEM = "INSERT INTO order_item(price, quantity, order_id, product_id) VALUES (?, ?, ?, ?);";

	public int insert(Order order) throws SQLException {
		try (PreparedStatement st = prepareStatementAndGetGeneratedKeys(INSERT_ORDER)) {
			st.setLong(1, order.getCode());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			return rs.getInt("id");

		}

	}

	public void insert(OrderItem orderItem) throws SQLException {
		try (PreparedStatement st = prepareStatement(INSERT_ORDER_ITEM)) {
			st.setDouble(1, orderItem.getPrice());
			st.setInt(2, orderItem.getQuantity());
			st.setInt(3, orderItem.getOrderId());
			st.setInt(4, orderItem.getProductId());
			st.executeUpdate();
		}
	}
}
