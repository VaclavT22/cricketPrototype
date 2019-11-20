package com.taska.java.crickets.cricketfarm.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taska.java.crickets.cricketfarm.model.Order;
import com.taska.java.crickets.cricketfarm.repository.OrderRepository;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping
	public List<Order> getOrders(){
		return orderRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Order getOrder(@PathVariable("id") Long id) {
		return orderRepository.getOne(id);
	}
	
	@PostMapping
	public Order createOrder(@RequestBody final Order order) {
		return orderRepository.saveAndFlush(order);
	}
	
	@PutMapping("/{id}")
	public Order updateOrder(@RequestBody final Order order, @PathVariable("id") Long id) {
		Order existingOrder = orderRepository.getOne(id);
		BeanUtils.copyProperties(order, existingOrder, "id");
		return orderRepository.saveAndFlush(existingOrder);
	}
	
	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable("id") Long id) {
		orderRepository.deleteById(id);
	}
}
