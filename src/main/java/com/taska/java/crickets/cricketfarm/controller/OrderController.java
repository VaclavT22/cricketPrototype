package com.taska.java.crickets.cricketfarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taska.java.crickets.cricketfarm.model.Order;
import com.taska.java.crickets.cricketfarm.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
	
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public List<Order> getOrders(){
		return orderService.getOrders();
	}
	
	@GetMapping("/{id}")
	public Order getOrder(@PathVariable("id") Long id) {
		return orderService.getOrderById(id);
	}
	
	@PostMapping
	public Order createOrder(@RequestBody final Order order) {
		return orderService.saveOrder(order);
	}
	
	@PutMapping("/{id}")
	public Order updateOrder(@RequestBody final Order order, @PathVariable("id") Long id) {
		return orderService.updateOrder(id, order);
	}
	
	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable("id") Long id) {
		orderService.deleteOrder(id);
	}
	
	@GetMapping("/completedOrders")
	public List<Order> getOrdersByCompletion(@RequestParam boolean isCompleted){
		return orderService.getCompletedOrders(isCompleted);
	}
	
	@GetMapping("/deliveredOrders")
	public List<Order> getOrdersByDelivery(@RequestParam boolean isDelivered) {
		return orderService.getDeliveredOrders(isDelivered);
	}
	
	@GetMapping("/completeOrder")
	public Order completeOrder(@RequestParam Long orderId) {
		return orderService.completeOrder(orderId);
	}
	
	//  http://localhost:8080/api/orders/deliverOrder?orderId=1&deliveryDate=2020-01-01
	@GetMapping("/deliverOrder")
	public Order deliverOrder(@RequestParam Long orderId, @RequestParam String deliveryDate) {
		return orderService.deliverOrder(orderId, deliveryDate);
	}
}
