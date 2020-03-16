package com.taska.java.crickets.cricketfarm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taska.java.crickets.cricketfarm.model.LineItem;
import com.taska.java.crickets.cricketfarm.model.Order;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {
	List<LineItem> findByOrderId(Order order);
}
