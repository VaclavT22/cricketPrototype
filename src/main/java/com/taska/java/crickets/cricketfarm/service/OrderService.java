package com.taska.java.crickets.cricketfarm.service;

import java.util.List;

import com.taska.java.crickets.cricketfarm.model.Order;


public interface OrderService {
	public List<Order> getOrders();
	public Order getOrderById(Long id);
	public Order saveOrder(Order order);
	public Order updateOrder(Long id, Order order);
	public void deleteOrder(Long id);
	public Order completeOrder(Long id);
	public Order deliverOrder(Long id, String deliveryDate);
	public Order updateOrderTotalPrice(Long orderId, Long productId, Integer amount);
	public List<Order> getCompletedOrders(boolean isCompleted);
	public List<Order> getDeliveredOrders(boolean isDelivered);
}
