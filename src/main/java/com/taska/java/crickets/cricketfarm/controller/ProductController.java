package com.taska.java.crickets.cricketfarm.controller;

import java.math.BigDecimal;
import java.util.Comparator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taska.java.crickets.cricketfarm.model.Product;
import com.taska.java.crickets.cricketfarm.repository.ProductRepository;
import com.taska.java.crickets.cricketfarm.utils.NumberVerifier;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private NumberVerifier numberVerifier;
	
	@GetMapping
	public List<Product> getProducts() {
		List<Product> products = productRepository.findAll();
		products.sort(Comparator.comparing(Product::getId));
		return products;
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
	
	@GetMapping("/likeName")
	public List<Product> getProductsLikeName(@RequestParam String productName) {
		return productRepository.findByNameIgnoreCaseContaining(productName);
	}
	
	@GetMapping("/likeAge")
	public List<Product> getProductsLikeAge(@RequestParam String productAge) {
		return productRepository.findByAgeIgnoreCaseContaining(productAge);
	}
	
	@GetMapping("/likeVolume")
	public List<Product> getProductsIsOfVolume(@RequestParam(name = "productVolume") String productVolumeString) {
		Float productVolume = 0f;
		if(numberVerifier.isAbleToParseFloat(productVolumeString)) {
			productVolume = Float.parseFloat(productVolumeString);
		}
		return productRepository.findByVolumeIs(productVolume);
	}
	
	@GetMapping("/likePackaging")
	public List<Product> getProductsLikePackaging(@RequestParam String productPackaging){
		return productRepository.findByPackagingIgnoreCaseContaining(productPackaging);
	}
	
	@GetMapping("/likePrice") 
	public List<Product> getProductsIsOfPrice(@RequestParam(name = "productPrice") String productPriceString) {
		BigDecimal productPrice;
		if(numberVerifier.isAbleToParseDecimal(productPriceString)) {
			productPrice = new BigDecimal(productPriceString);
		} else {
			productPrice = new BigDecimal(0);
		}
		return productRepository.findByPriceIs(productPrice);
	}
}
