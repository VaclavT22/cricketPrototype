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

import com.taska.java.crickets.cricketfarm.model.Product;
import com.taska.java.crickets.cricketfarm.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping
	public List<Product> getProducts() {
		return productRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable("id") Long id) {
		return productRepository.getOne(id);
	}
	
	@PostMapping
	public Product createProduct(@RequestBody final Product product) {
		return productRepository.saveAndFlush(product);
	}
	
	@PutMapping("/{id}")
	public Product updateProduct(@RequestBody final Product product, @PathVariable("id") Long id) {
		Product existingProduct = productRepository.getOne(id);
		BeanUtils.copyProperties(product, existingProduct, "id");
		return productRepository.saveAndFlush(existingProduct);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable("id") Long id) {
		productRepository.deleteById(id);
	}
}
