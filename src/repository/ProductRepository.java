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

import javax.management.RuntimeErrorException;

import model.Product;
import model.User;
import util.DBUtil;

public class ProductRepository {

	private static final String SELECT_ALL = "SELECT * FROM product;";
	private static final String INSERT = "INSERT INTO product (name, code, quantity, price) VALUES (?, ?, ?, ?);";
	private static final String EXIST = "SELECT COUNT(*) FROM product WHERE code=?";
	private static final String FIND_BY_CODE = "SELECT * FROM product WHERE code=?";
	private static final String UPDATE = "UPDATE product SET name=?, quantity=?, price=? WHERE code=?;";
	private static final String DELETE = "DELETE FROM product WHERE code=?;";
	private static final String UPDATE_QUANTITY = "UPDATE product SET quantity= quantity-? WHERE code=?;";

	public List<Product> getProduct() {

		try (Statement st = DBUtil.connect().createStatement(); ResultSet rs = st.executeQuery(SELECT_ALL)) {
			return extractProductsFromResultSet(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
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

	public boolean insert(Product prod) {
		try (PreparedStatement st = DBUtil.connect().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			st.setString(1, prod.getName());
			st.setString(2, prod.getCode());
			st.setInt(3, prod.getQuantity());
			st.setDouble(4, prod.getPrice());
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean exist(String code) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(EXIST)) {

			st.setString(1, code);
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

	public Product findByCode(String code) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(FIND_BY_CODE)) {

			st.setString(1, code);
			ResultSet rs = st.executeQuery();
			rs.next();
			return extractProduct(rs);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public void update(Product prod) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(UPDATE)) {
			st.setString(1, prod.getName());
			st.setInt(2, prod.getQuantity());
			st.setDouble(3, prod.getPrice());
			st.setString(4, prod.getCode());

			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(String code) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(DELETE)) {
			st.setString(1, code);
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void removeQuantity(String code, int quantity) {
		try (PreparedStatement st = DBUtil.connect().prepareStatement(UPDATE_QUANTITY)) {
			st.setInt(1, quantity);
			st.setString(2, code);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Error");
		}

	}

}
