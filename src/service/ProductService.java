package service;

import java.util.List;

import model.Product;
import model.User;
import repository.ProductRepository;
import repository.UserRepository;

public class ProductService {

	private ProductRepository productRepository;

	public ProductService() {
		productRepository = new ProductRepository();
	}

	public Product findByCode(String code) {
		// TODO validation if code null or empy
		if (productRepository.exist(code)) {
			return productRepository.findByCode(code);
		}
		throw new RuntimeException("Product does not exist");

	}

	public List<Product> getProducts() {
		return productRepository.getProduct();
	}

	// control if in stock if empty insert
	public boolean insert(Product prod) {

		if (productRepository.exist(prod.getCode())) {

			throw new RuntimeException("In Stock");
		}

		return productRepository.insert(prod);

	}

	public void update(Product prod) {

		if (productRepository.exist(prod.getCode())) {
			productRepository.update(prod);
		} else {
			throw new RuntimeException("Product not exist");
		}
		
	}
	
	public void delete(String code) {
		if (productRepository.exist(code)) {
			productRepository.delete(code);
		} else {
			throw new RuntimeException("Product not exist");
		}
	}

}
