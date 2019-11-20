package com.taska.java.crickets.cricketfarm.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taska.java.crickets.cricketfarm.model.Order;
import com.taska.java.crickets.cricketfarm.repository.OrderRepository;
import com.taska.java.crickets.cricketfarm.repository.ProductRepository;

@Service
public class LineItemServiceImpl implements LineItemService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Override
	public void updateOrderPrice(Long orderId, Long productId, int amount) {
		Order order = orderRepository.getOne(orderId);
		if(order.getTotalPrice() == null) {
			final BigDecimal newPrice = productRepository.getOne(productId).getPrice().multiply(new BigDecimal(amount)); 
			order.setTotalPrice(newPrice);
		} else {
			final BigDecimal newPrice = order.getTotalPrice().add(productRepository.getOne(productId).getPrice().multiply(new BigDecimal(amount)));
			order.setTotalPrice(newPrice);
		}
		
	}
	
}
