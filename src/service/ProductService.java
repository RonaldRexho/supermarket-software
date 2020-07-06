package service;

import java.sql.SQLException;
import java.util.List;

import exception.SupermarketException;
import model.Product;
import repository.ProductRepository;

public class ProductService {

	private ProductRepository productRepository;

	public ProductService() {
		productRepository = new ProductRepository();
	}

	public Product findByCode(String code) {
		try {

			if (productRepository.exist(code)) {
				return productRepository.findByCode(code);
			} else {
				throw new SupermarketException("Product does not exist");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}
	}

	public List<Product> getProducts() {
		try {
			return productRepository.getProducts();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}
	}

	public void insert(Product prod) {
		try {

			if (productRepository.exist(prod.getCode())) {
				throw new SupermarketException("Product already inserted");
			} else {
				productRepository.insert(prod);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}
	}

	public void update(Product prod) {
		try {

			if (productRepository.exist(prod.getCode())) {
				productRepository.update(prod);
			} else {
				throw new SupermarketException("Product does not exist");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}
	}

	public void delete(String code) {
		try {

			if (productRepository.exist(code)) {
				productRepository.delete(code);
			} else {
				throw new SupermarketException("Product not exist");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}
	}

}
