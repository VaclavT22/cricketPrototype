package com.taska.java.crickets.cricketfarm.serviceimpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taska.java.crickets.cricketfarm.model.Order;
import com.taska.java.crickets.cricketfarm.model.Product;
import com.taska.java.crickets.cricketfarm.repository.OrderRepository;
import com.taska.java.crickets.cricketfarm.service.IncomeService;
import com.taska.java.crickets.cricketfarm.service.OrderService;
import com.taska.java.crickets.cricketfarm.service.ProductService;
import com.taska.java.crickets.cricketfarm.utils.DateVerifier;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderRepository orderRepository;
	
//	@Autowired
//	private NumberVerifier numberVerifier;
//	
	@Autowired
	private DateVerifier dateVerifier;
	
	@Autowired
	private IncomeService incomeService;
	
	

	@Override
	public List<Order> getOrders() {
		return orderRepository.findAll();
	}
	
	@Override
	public Order getOrderById(Long id) {
		return orderRepository.getOne(id);
	}

	@Override
	public Order saveOrder(Order order) {
		return orderRepository.saveAndFlush(order);
	}

	@Override
	public Order updateOrder(Long id, Order order) {
		Order existingOrder = orderRepository.getOne(id);
		BeanUtils.copyProperties(order, existingOrder, "id");
		return orderRepository.saveAndFlush(existingOrder);
	}

	@Override
	public void deleteOrder(Long id) {
		orderRepository.deleteById(id);
		
	}
	
	@Override
	public Order completeOrder(Long id) {
		Order order = getOrderById(id);
		order.setIsCompleted(true);
		return updateOrder(id, order);
	}
	
	@Override
	public Order deliverOrder(Long id, String deliveryDate) {
		Order order = getOrderById(id);
		if(!order.getIsCompleted()) {
			return order;
		}
		if(dateVerifier.isDateCorrect(deliveryDate)) {
			order.setDeliveryDate(LocalDate.parse(deliveryDate));
		} else {
			order.setDeliveryDate(LocalDate.now());
		}
		order.setIsDelivered(true);
		incomeService.generateIncome(order);
		return updateOrder(id, order);
	}

	@Override
	public Order updateOrderTotalPrice(Long orderId, Long productId, Integer amount) {
		Order order = orderRepository.getOne(orderId);
		Product product = productService.getProductById(productId);
		if(order.getTotalPrice() == null) {
			final BigDecimal newPrice = product.getPrice().multiply(new BigDecimal(amount)); 
			order.setTotalPrice(newPrice);
		} else {
			final BigDecimal newPrice = order.getTotalPrice().add(product.getPrice().multiply(new BigDecimal(amount)));
			order.setTotalPrice(newPrice);
		}
		return updateOrder(orderId, order);
	}

	@Override
	public List<Order> getCompletedOrders(boolean isCompleted) {
		return orderRepository.findByIsCompleted(isCompleted);
	}
	
	@Override
	public List<Order> getDeliveredOrders(boolean isDelivered) {
		return orderRepository.findByIsDelivered(isDelivered);
	}
}
