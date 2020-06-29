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

import model.Product;
import model.User;
import util.DBUtil;

public class ProductRepository {

	private static final String SELECT_ALL = "SELECT * FROM \"product\";";
	private static final String INSERT = "INSERT INTO public.product(\r\n"
			+ "	id, name, code, supplier_id, category, purchase_date, expire_on, quantity, price,purches_price)\r\n"
			+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,);";
	private static final String EXIST = "SELECT COUNT(*) FROM \\\"product\\\" WHERE code=?";
	private static final String FIND_BY_ID = "SELECT * FROM \"product\" WHERE id=?";
	private static final String UPDATE = "UPDATE public.product\r\n"
			+ "	SET name=?, code=?, supplier_id=?, category=?, purchase_date=?, expire_on=?, quantity=?, price=?, created_on=?, modified_on=?, purches_price=?\r\n"
			+ "	WHERE id=?;";

	public List<Product> getProduct() {

		try (Statement st = DBUtil.connect().createStatement(); ResultSet rs = st.executeQuery(SELECT_ALL)) {
			return extractProductsFromResultSet(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private List<Product> extractProductsFromResultSet(ResultSet rs) throws SQLException {
		List<Product> products = new ArrayList();

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
		prod.setCategory(rs.getInt("category"));
		prod.setName(rs.getString("name"));
		prod.setPrice(rs.getDouble("price"));
		prod.setPurchesedPrice(rs.getDouble("purches_price"));
		prod.setQuantity(rs.getInt("quantity"));
		prod.setSupplierId(rs.getInt("supplier_id"));
		prod.setExpiredOn(rs.getDate("expire_on"));
		prod.setPurchasedDate(rs.getDate("purchase_date"));
		return prod;

	}

	public Product insert(Product prod) {
		try (PreparedStatement st = DBUtil.connect().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			st.setInt(1, prod.getId());
			st.setString(2, prod.getName());
			st.setString(3, prod.getCode());
			st.setInt(4, prod.getSupplierId());
			st.setInt(5, prod.getCategory());
			st.setDate(6, prod.getPurchasedDate());
			st.setDate(7, prod.getExpiredOn());
			st.setInt(8, prod.getQuantity());
			st.setDouble(9, prod.getPrice());
			st.setDouble(10, prod.getPurchesedPrice());
			st.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return prod;

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

	public Product findById(int id) {

		Product prod = null;

		try (PreparedStatement st = DBUtil.connect().prepareStatement(FIND_BY_ID)) {

			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				prod = extractProduct(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return prod;
	}

	public Product update(Product prod) {

		try (PreparedStatement st = DBUtil.connect().prepareStatement(UPDATE)) {
			st.setInt(1, prod.getId());
			st.setString(2, prod.getName());
			st.setString(3, prod.getCode());
			st.setInt(4, prod.getSupplierId());
			st.setInt(5, prod.getCategory());
			st.setDate(6, prod.getPurchasedDate());
			st.setDate(7, prod.getExpiredOn());
			st.setInt(8, prod.getQuantity());
			st.setDouble(9, prod.getPrice());
			st.setDouble(10, prod.getPurchesedPrice());

			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return prod;

	}

}
