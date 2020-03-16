package com.taska.java.crickets.cricketfarm.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.NotNull;

import com.taska.java.crickets.cricketfarm.model.Product;
import com.taska.java.crickets.cricketfarm.repository.ProductRepository;

class ProductServiceImplTest {
	
	private static final Product PRODUCT = new Product();
	private static final String PRODUCT_NAME = "Cricket";
	private static final String PRODUCT_AGE = "Young";
	private static final Float VOLUME = 1.0f;
	private static final String PACKAGING = "Box";
	private static final BigDecimal PRICE = new BigDecimal(250);

	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
		PRODUCT.setId(1L);
		PRODUCT.setName(PRODUCT_NAME);
		PRODUCT.setAge(PRODUCT_AGE);
		PRODUCT.setVolume(VOLUME);
		PRODUCT.setPackaging(PACKAGING);
		PRODUCT.setPrice(PRICE);
	}
	
	@Test
	void getAllProducts() {
		when(productRepository.findAll()).thenReturn(Arrays.asList(PRODUCT));
		List<Product> products = productService.getAllProducts();
		assertFalse(products.isEmpty());
	}
	
	@Test
	void getProductById() {
		ArgumentCaptor<Long> captLongId = ArgumentCaptor.forClass(Long.class);
		when(productRepository.getOne(captLongId.capture())).thenReturn(PRODUCT);
		Product actual = productService.getProductById(1L);
		verify(productRepository, times(1)).getOne(captLongId.capture());
		assertAll(
				() -> assertTrue(actual != null),
				() -> assertEquals(1L, captLongId.getValue())
				);
	}
	
	@Test
	void getProductsByName() {
		ArgumentCaptor<String> captName = ArgumentCaptor.forClass(String.class);
		when(productRepository.findByNameIgnoreCaseContaining(captName.capture()))
			.thenReturn(Arrays.asList(PRODUCT));
		List<Product> actual = productService.getProductsLikeName("Cr");
		verify(productRepository, times(1)).findByNameIgnoreCaseContaining(captName.capture());
		assertAll(
				() -> assertFalse(actual.isEmpty()),
				() -> assertEquals("Cr", captName.getValue())
				);
	}
	
	@Test
	void getProductsByAge() {
		ArgumentCaptor<String> captAge = ArgumentCaptor.forClass(String.class);
		when(productRepository.findByAgeIgnoreCaseContaining(captAge.capture()))
			.thenReturn(Arrays.asList(PRODUCT));
		List<Product> actual = productService.getProductsLikeAge("y");
		verify(productRepository, times(1)).findByAgeIgnoreCaseContaining(captAge.capture());
		assertAll(
				() -> assertFalse(actual.isEmpty()),
				() -> assertEquals("y", captAge.getValue())
				);
	}
	
	@Test
	void getProductsByVolume() {
		ArgumentCaptor<Float> captVolume = ArgumentCaptor.forClass(Float.class);
		when(productRepository.findByVolumeIs(captVolume.capture()))
			.thenReturn(Arrays.asList(PRODUCT));
		List<Product> actual = productService.getProductsByVolume(1.0f);
		verify(productRepository, times(1)).findByVolumeIs(captVolume.capture());
		assertAll(
				() -> assertFalse(actual.isEmpty()),
				() -> assertEquals(1.0f, captVolume.getValue())
				);
	}
	
	@Test
	void getProductsByPackaging() {
		ArgumentCaptor<String> captPackaging = ArgumentCaptor.forClass(String.class);
		when(productRepository.findByPackagingIgnoreCaseContaining(captPackaging.capture()))
			.thenReturn(Arrays.asList(PRODUCT));
		List<Product> actual = productService.getProductsLikePackaging("B");
		verify(productRepository, times(1)).findByPackagingIgnoreCaseContaining(captPackaging.capture());
		assertAll(
				() -> assertFalse(actual.isEmpty()),
				() -> assertEquals("B", captPackaging.getValue())
				);
	}
	
	@Test
	void getProductsByPrice() {
		ArgumentCaptor<BigDecimal> captPrice = ArgumentCaptor.forClass(BigDecimal.class);
		when(productRepository.findByPriceIs(captPrice.capture())).thenReturn(Arrays.asList(PRODUCT));
		List<Product> actual = productService.getProductsByPrice(PRICE);
		verify(productRepository, times(1)).findByPriceIs(captPrice.capture());
		assertAll(
				() -> assertFalse(actual.isEmpty()),
				() -> assertEquals(PRICE, captPrice.getValue())
				);
	}
	
	@Test
	void saveProduct() {
		ArgumentCaptor<Product> captProduct = ArgumentCaptor.forClass(Product.class);
		when(productRepository.saveAndFlush(captProduct.capture())).thenReturn(PRODUCT);
		Product actual = productService.saveProduct(PRODUCT);
		verify(productRepository, times(1)).saveAndFlush(captProduct.capture());
		assertAll(
				() -> assertTrue(actual != null),
				() -> assertEquals("Cricket", actual.getName()),
				() -> assertEquals("Cricket", captProduct.getValue().getName())
				);
	}
	
	@Test
	void updateProduct() {
		Product updatedProduct = new Product();
		updatedProduct.setAge("Old");
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Product> captProduct = ArgumentCaptor.forClass(Product.class);
		when(productRepository.getOne(captLong.capture())).thenReturn(PRODUCT).thenReturn(PRODUCT);
		when(productRepository.saveAndFlush(captProduct.capture())).then(invocation -> {
			Product temp = invocation.getArgument(0, Product.class);
			return temp;
		});
		Product actual = productService.updateProduct(1L, updatedProduct);
		verify(productRepository, times(1)).getOne(captLong.capture());
		verify(productRepository, times(1)).saveAndFlush(captProduct.capture());
		assertAll(
				() -> assertTrue(actual != null),
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertEquals(1L, actual.getId()),
				() -> assertEquals("Old", actual.getAge())
				);
	}
	
	@Test
	void deleteProduct() {
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		doNothing().when(productRepository).deleteById(captLong.capture());
		productService.deleteProduct(1L);
		verify(productRepository, times(1)).deleteById(captLong.capture());
		assertEquals(1L, captLong.getValue());
	}
}
