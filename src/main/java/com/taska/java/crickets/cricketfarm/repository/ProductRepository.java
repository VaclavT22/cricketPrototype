package com.taska.java.crickets.cricketfarm.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taska.java.crickets.cricketfarm.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByNameIgnoreCaseContaining(String productName);
	List<Product> findByAgeIgnoreCaseContaining(String productAge);
	List<Product> findByVolumeContaining(Float productVolume);
	List<Product> findByPackagingIgnoreCaseContaining(String productPackaging);
	List<Product> findByPriceContaining(BigDecimal productPrice);
}
