package service;

import java.sql.SQLException;
import java.util.List;

import exception.SupermarketException;
import model.Order;
import model.OrderItem;
import repository.OrderRepository;

public class OrderService {

	private OrderRepository orderRepository;

	public OrderService() {
		orderRepository = new OrderRepository();
	}

	public void insert(List<OrderItem> orderItems) {
		try {
			Order order = new Order();
			int orderId = orderRepository.insert(order);

			setOrderId(orderItems, orderId);

			for (OrderItem orderItem : orderItems) {
				orderRepository.insert(orderItem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SupermarketException("Cannot connect to database");
		}
	}

	private void setOrderId(List<OrderItem> orderItems, int orderId) {
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
		}
	}
}
