package com.taska.java.crickets.cricketfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taska.java.crickets.cricketfarm.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
