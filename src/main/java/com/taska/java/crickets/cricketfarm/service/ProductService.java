package com.taska.java.crickets.cricketfarm.service;

import java.math.BigDecimal;
import java.util.List;

import com.taska.java.crickets.cricketfarm.model.Product;

public interface ProductService {

	public List<Product> getAllProducts();
	public List<Product> getProductsLikeName(String likeName);
	public List<Product> getProductsLikeAge(String likeAge);
	public List<Product> getProductsByVolume(Float volume);
	public List<Product> getProductsLikePackaging(String likePackaging);
	public List<Product> getProductsByPrice(BigDecimal price);
	public Product getProductById(Long id);
	public Product saveProduct(Product product);
	public Product updateProduct(Long id, Product product);
	public void deleteProduct(Long id);
}
