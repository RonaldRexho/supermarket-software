package service;

import java.time.LocalDate;
import java.util.List;

import model.Order;
import model.OrderItem;
import repository.OrderRepository;
import repository.ProductRepository;

public class OrderService {

	private OrderRepository orderRepository;
	private ProductRepository productRepository;

	public OrderService() {
		orderRepository = new OrderRepository();
		productRepository = new ProductRepository();

	}

	public void insert(List<OrderItem> orderItems) {
		Order order = new Order();
		long code = System.currentTimeMillis();
		order.setCode(code);

		int orderId = orderRepository.insert(order);
		setOrderId(orderItems, orderId);

		for (OrderItem orderItem : orderItems) {
			orderRepository.insertOrderItem(orderItem);
			productRepository.removeQuantity(orderItem.getCode(), orderItem.getQuantity());
		}
	}

	private void setOrderId(List<OrderItem> orderItems, int orderId) {
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
		}
	}
}
