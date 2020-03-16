package com.taska.java.crickets.cricketfarm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taska.java.crickets.cricketfarm.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByOrderDate(LocalDate orderDate);
	List<Order> findByDeliveryDate(LocalDate deliveryDate);
	List<Order> findByIsCompleted(Boolean isCompleted);
	List<Order> findByIsDelivered(Boolean isDelivered);
}
