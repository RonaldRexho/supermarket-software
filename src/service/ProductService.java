package service;

import java.util.List;

import repository.ProductRepository;
import repository.UserRepository;
import repository.entity.Product;
import repository.entity.User;

public class ProductService {

	private ProductRepository productRepository;

	public ProductService() {
		productRepository = new ProductRepository();
	}

	public List<Product> getProduct() {
		return productRepository.getProduct();
	}

	// control if in stock if empty insert
	public Product insert(Product prod) {

		if (productRepository.exist(prod.getCode())) {

			throw new RuntimeException("In Stock");
		}

		return productRepository.insert(prod);

	}

	public Product update(Product prod) {

		if (productRepository.exist(prod.getCode())) {

			return productRepository.update(prod);
		}
		return prod;
	}

}
