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

	public void insert(Product product) {
		validateProduct(product);
		try {

			if (productRepository.exist(product.getCode())) {
				throw new SupermarketException("Product already inserted");
			} else {
				productRepository.insert(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}
	}

	public void update(Product product) {
		validateProduct(product);
		try {

			if (productRepository.exist(product.getCode())) {
				productRepository.update(product);
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
	
	private void validateProduct(Product product) {
		boolean failed = false;
		StringBuilder message = new StringBuilder("Validation error: ");
		if (product.getCode().isEmpty()) {
			failed = true;
			message.append("\nCode is empty");
		}
		if (product.getName().isEmpty()) {
			failed = true;
			message.append("\nName is empty");
		}
		if (product.getPrice() <= 0) {
			failed = true;
			message.append("\nPrice is a negative number");
		}
		if (product.getQuantity() < 0) {
			failed = true;
			message.append("\nQuantity is a negative number");
		}
		if (failed) {
			throw new SupermarketException(message.toString());
		}
	}

}
