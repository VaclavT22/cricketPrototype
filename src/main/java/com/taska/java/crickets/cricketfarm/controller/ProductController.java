package com.taska.java.crickets.cricketfarm.controller;

import java.math.BigDecimal;
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

import com.taska.java.crickets.cricketfarm.model.Product;
import com.taska.java.crickets.cricketfarm.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

	
	@Autowired
	private ProductService productService;

	
	@GetMapping
	public List<Product> getProducts() {
		return productService.getAllProducts();
	}
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable("id") Long id) {
		return productService.getProductById(id);
	}
	
	@PostMapping
	public Product createProduct(@RequestBody final Product product) {
		return productService.saveProduct(product);
	}
	
	@PutMapping("/{id}")
	public Product updateProduct(@RequestBody final Product product, @PathVariable("id") Long id) {
		return productService.updateProduct(id, product);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable("id") Long id) {
		productService.deleteProduct(id);
	}
	
	@GetMapping("/likeName")
	public List<Product> getProductsLikeName(@RequestParam String productName) {
		return productService.getProductsLikeName(productName);
	}
	
	@GetMapping("/likeAge")
	public List<Product> getProductsLikeAge(@RequestParam String productAge) {
		return productService.getProductsLikeAge(productAge);
	}
	
	@GetMapping("/likeVolume")
	public List<Product> getProductsIsOfVolume(@RequestParam Float productVolume) {
		return productService.getProductsByVolume(productVolume);
	}
	
	@GetMapping("/likePackaging")
	public List<Product> getProductsLikePackaging(@RequestParam String productPackaging){
		return productService.getProductsLikePackaging(productPackaging);
	}
	
	@GetMapping("/likePrice") 
	public List<Product> getProductsIsOfPrice(@RequestParam(name = "productPrice") BigDecimal productPrice) {
		return productService.getProductsByPrice(productPrice);
	}
}
