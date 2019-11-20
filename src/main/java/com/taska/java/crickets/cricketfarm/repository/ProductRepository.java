package com.taska.java.crickets.cricketfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taska.java.crickets.cricketfarm.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
