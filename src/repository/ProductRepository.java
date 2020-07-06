package repository;

import static util.DBUtil.createStatement;
import static util.DBUtil.prepareStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductRepository {

	private static final String SELECT_ALL = "SELECT * FROM product ORDER BY name;";
	private static final String INSERT = "INSERT INTO product (name, code, quantity, price) VALUES (?, ?, ?, ?);";
	private static final String EXIST = "SELECT COUNT(*) FROM product WHERE code=?";
	private static final String FIND_BY_CODE = "SELECT * FROM product WHERE code=?";
	private static final String UPDATE = "UPDATE product SET name=?, quantity=?, price=? WHERE code=?;";
	private static final String DELETE = "DELETE FROM product WHERE code=?;";
	private static final String REDUCE_QUANTITY = "UPDATE product SET quantity= quantity-? WHERE code=?;";

	public List<Product> getProducts() throws SQLException {
		try (Statement st = createStatement()) {
			ResultSet rs = st.executeQuery(SELECT_ALL);
			return extractProductsFromResultSet(rs);
		}
	}

	public void insert(Product prod) throws SQLException {
		try (PreparedStatement st = prepareStatement(INSERT)) {
			st.setString(1, prod.getName());
			st.setString(2, prod.getCode());
			st.setInt(3, prod.getQuantity());
			st.setDouble(4, prod.getPrice());
			st.executeUpdate();
		}
	}

	public boolean exist(String code) throws SQLException {
		try (PreparedStatement st = prepareStatement(EXIST)) {
			st.setString(1, code);
			ResultSet rs = st.executeQuery();
			rs.next();
			
			long count = rs.getLong("count");
			return count > 0;
	
		}
	}

	public Product findByCode(String code) throws SQLException {
		try (PreparedStatement st = prepareStatement(FIND_BY_CODE)) {
			st.setString(1, code);
			ResultSet rs = st.executeQuery();
			rs.next();
			return extractProduct(rs);
		}

	}

	public void update(Product prod) throws SQLException {
		try (PreparedStatement st = prepareStatement(UPDATE)) {
			st.setString(1, prod.getName());
			st.setInt(2, prod.getQuantity());
			st.setDouble(3, prod.getPrice());
			st.setString(4, prod.getCode());
			st.executeUpdate();
		} 

	}

	public void delete(String code) throws SQLException {
		try (PreparedStatement st = prepareStatement(DELETE)) {
			st.setString(1, code);
			st.executeUpdate();
		}

	}

	public void reduceQuantity(String code, int quantity) throws SQLException {
		try (PreparedStatement st = prepareStatement(REDUCE_QUANTITY)) {
			st.setInt(1, quantity);
			st.setString(2, code);
			st.executeUpdate();
		}

	}
	
	private List<Product> extractProductsFromResultSet(ResultSet rs) throws SQLException {
		List<Product> products = new ArrayList<>();
		while (rs.next()) {
			Product prod = extractProduct(rs);
			products.add(prod);
		}
		return products;
	}

	private Product extractProduct(ResultSet rs) throws SQLException {
		Product prod = new Product();
		prod.setId(rs.getInt("id"));
		prod.setCode(rs.getString("code"));
		prod.setName(rs.getString("name"));
		prod.setPrice(rs.getDouble("price"));
		prod.setQuantity(rs.getInt("quantity"));
		return prod;

	}

}
