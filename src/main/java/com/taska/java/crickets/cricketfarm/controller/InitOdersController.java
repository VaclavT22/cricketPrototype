package com.taska.java.crickets.cricketfarm.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taska.java.crickets.cricketfarm.model.LineItem;
import com.taska.java.crickets.cricketfarm.model.Order;
import com.taska.java.crickets.cricketfarm.model.Product;
import com.taska.java.crickets.cricketfarm.repository.CustomerRepository;
import com.taska.java.crickets.cricketfarm.repository.LineItemRepository;
import com.taska.java.crickets.cricketfarm.repository.OrderRepository;
import com.taska.java.crickets.cricketfarm.repository.ProductRepository;
import com.taska.java.crickets.cricketfarm.service.LineItemService;

@RestController
@RequestMapping("/initOrders")
public class InitOdersController {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private LineItemRepository lineItemRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private LineItemService lineItemService;
	
	@GetMapping
	public String initData() {
		Order order1 = new Order();
		Order order2 = new Order();
		Order order3 = new Order();
		
		order1.setCustomer(customerRepository.getOne(1L));
		order1.setIsCompleted(true);
		order1.setOrderDate(LocalDate.of(2019, 12, 20));
		
		order2.setCustomer(customerRepository.getOne(2L));
		order2.setIsCompleted(false);
		order2.setOrderDate(LocalDate.of(2020, 1, 20));
		
		order3.setCustomer(customerRepository.getOne(1L));
		order3.setIsCompleted(false);
		order3.setOrderDate(LocalDate.of(2020, 1, 20));
		
		orderRepository.save(order1);
		orderRepository.save(order2);
		orderRepository.save(order3);
		
		return "Orders were loaded to DB";
	}
	
	@GetMapping("/init")
	public String initLineItemsData() {
		List<Order> orders = orderRepository.findAll();
		List<Product> products = productRepository.findAll();
		LineItem item1 = new LineItem();
		LineItem item2 = new LineItem();
		LineItem item3 = new LineItem();
		LineItem item4 = new LineItem();
		LineItem item5 = new LineItem();
		LineItem item6 = new LineItem();
		LineItem item7 = new LineItem();
		LineItem item8 = new LineItem();
		
		item1.setOrderId(orders.get(0));
		item2.setOrderId(orders.get(0));
		item3.setOrderId(orders.get(1));
		item4.setOrderId(orders.get(1));
		item5.setOrderId(orders.get(1));
		item6.setOrderId(orders.get(2));
		item7.setOrderId(orders.get(2));
		item8.setOrderId(orders.get(2));
		
		item1.setProductId(products.get(0));
		item2.setProductId(products.get(1));
		item3.setProductId(products.get(2));
		item4.setProductId(products.get(1));
		item5.setProductId(products.get(3));
		item6.setProductId(products.get(4));
		item7.setProductId(products.get(0));
		item8.setProductId(products.get(6));
		
		item1.setAmount(2);
		item2.setAmount(1);
		item3.setAmount(4);
		item4.setAmount(1);
		item5.setAmount(3);
		item6.setAmount(1);
		item7.setAmount(6);
		item8.setAmount(1);
		
		lineItemService.updateOrderPrice(item1.getOrderId().getId(), item1.getProductId().getId(), item1.getAmount());
		lineItemRepository.save(item1);
		lineItemService.updateOrderPrice(item2.getOrderId().getId(), item2.getProductId().getId(), item2.getAmount());
		lineItemRepository.save(item2);
		lineItemService.updateOrderPrice(item3.getOrderId().getId(), item3.getProductId().getId(), item3.getAmount());
		lineItemRepository.save(item3);
		lineItemService.updateOrderPrice(item4.getOrderId().getId(), item4.getProductId().getId(), item4.getAmount());
		lineItemRepository.save(item4);
		lineItemService.updateOrderPrice(item5.getOrderId().getId(), item5.getProductId().getId(), item5.getAmount());
		lineItemRepository.save(item5);
		lineItemService.updateOrderPrice(item6.getOrderId().getId(), item6.getProductId().getId(), item6.getAmount());
		lineItemRepository.save(item6);
		lineItemService.updateOrderPrice(item7.getOrderId().getId(), item7.getProductId().getId(), item7.getAmount());
		lineItemRepository.save(item7);
		lineItemService.updateOrderPrice(item8.getOrderId().getId(), item8.getProductId().getId(), item8.getAmount());
		lineItemRepository.save(item8);
		
		
		return "Line items were <b>saved<//b> to Database! Also orders were adjusted!";
	}
}
