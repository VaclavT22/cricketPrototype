package com.taska.java.crickets.cricketfarm.serviceimpl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taska.java.crickets.cricketfarm.model.Product;
import com.taska.java.crickets.cricketfarm.repository.ProductRepository;
import com.taska.java.crickets.cricketfarm.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {
		List<Product> products = productRepository.findAll();
		products.sort(Comparator.comparing(Product::getId));
		return products;
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.getOne(id);
	}

	@Override
	public List<Product> getProductsLikeName(String likeName) {
		return productRepository.findByNameIgnoreCaseContaining(likeName);
	}

	@Override
	public List<Product> getProductsLikeAge(String likeAge) {
		return productRepository.findByAgeIgnoreCaseContaining(likeAge);
	}

	@Override
	public List<Product> getProductsByVolume(Float volume) {
//		Float productVolume = 0f;
//		if(numberVerifier.isAbleToParseFloat(productVolumeString)) {
//			productVolume = Float.parseFloat(productVolumeString);
//		}
		return productRepository.findByVolumeIs(volume);
	}

	@Override
	public List<Product> getProductsLikePackaging(String likePackaging) {
		return productRepository.findByPackagingIgnoreCaseContaining(likePackaging);
	}

	@Override
	public List<Product> getProductsByPrice(BigDecimal price) {
//		BigDecimal productPrice;
//		if(numberVerifier.isAbleToParseDecimal(productPriceString)) {
//			productPrice = new BigDecimal(productPriceString);
//		} else {
//			productPrice = new BigDecimal(0);
//		}
		return productRepository.findByPriceIs(price);
	}

	@Override
	public Product saveProduct(Product product) {
		return productRepository.saveAndFlush(product);
	}

	@Override
	public Product updateProduct(Long id, Product product) {
		Product existingProduct = productRepository.getOne(id);
		BeanUtils.copyProperties(product, existingProduct, "id");
		return productRepository.saveAndFlush(existingProduct);
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}
