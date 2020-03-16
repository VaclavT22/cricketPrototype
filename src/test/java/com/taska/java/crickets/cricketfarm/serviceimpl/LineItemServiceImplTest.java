package com.taska.java.crickets.cricketfarm.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taska.java.crickets.cricketfarm.model.LineItem;
import com.taska.java.crickets.cricketfarm.model.Order;
import com.taska.java.crickets.cricketfarm.model.Product;
import com.taska.java.crickets.cricketfarm.repository.LineItemRepository;
import com.taska.java.crickets.cricketfarm.service.OrderService;

class LineItemServiceImplTest {

	private static final Long ID = 1L;
	private static final List<LineItem> LINEITEM_LIST = new ArrayList<>();
	private static final LineItem LINEITEM = new LineItem();
	private static final Order ORDER = new Order();
	private static final Product PRODUCT = new Product();
	private static final Float PRODUCT_VOLUME = 0.5f;
	private static final BigDecimal PRODUCT_PRICE = new BigDecimal(100);
	private static final String PRODUCT_NAME = "Product";
	private static final String PRODUCT_AGE = "Young";
	private static final String PRODUCT_PACKAGE = "Box";
	
	
	@Mock
	private LineItemRepository lineItemRepository;
	
//	@Mock
//	private OrderRepository orderRepository;
//	
//	@Mock
//	private ProductRepository productRepository;
	
	@InjectMocks
	private LineItemServiceImpl lineItemService;
	
	@Mock
	private OrderService orderService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
		ORDER.setId(ID);
		ORDER.setTotalPrice(null);
		PRODUCT.setId(ID);
		PRODUCT.setAge(PRODUCT_AGE);
		PRODUCT.setPackaging(PRODUCT_PACKAGE);
		PRODUCT.setPrice(PRODUCT_PRICE);
		PRODUCT.setVolume(PRODUCT_VOLUME);
		PRODUCT.setName(PRODUCT_NAME);
		LINEITEM.setAmount(2);
		LINEITEM.setId(ID);
		LINEITEM.setOrderId(ORDER);
		LINEITEM.setProductId(PRODUCT);
		LINEITEM_LIST.clear();
		LINEITEM_LIST.add(new LineItem());
	}
	
	@Test
	void getAllLineItems() {
		when(lineItemRepository.findAll()).thenReturn(LINEITEM_LIST);
		List<LineItem> temp = lineItemService.getLineItems();
		assertAll( 
				() -> assertEquals(true, !temp.isEmpty()),
				() -> assertEquals(1, temp.size())
				);
	}
	
	@Test
	void getLineItemById() {
		ArgumentCaptor<Long> captId = ArgumentCaptor.forClass(Long.class);
		when(lineItemRepository.getOne(captId.capture())).thenReturn(LINEITEM);
		LineItem temp = lineItemService.getLineItemById(1L);
		assertAll(
				() -> assertEquals(1L, captId.getValue()),
				() -> assertEquals(2, temp.getAmount())
				);
	}
	
	@Test
	void updateOrderPriceIsStartingWithNull() {
		ORDER.setTotalPrice(new BigDecimal(200));
		when(orderService.updateOrderTotalPrice(ID, ID, 2)).thenReturn(ORDER);
		lineItemService.updateOrderPrice(ID, ID, LINEITEM.getAmount());
		assertEquals(new BigDecimal(200), ORDER.getTotalPrice());
	}
	
	
	@Test
	void saveLineItem() {
		ORDER.setTotalPrice(new BigDecimal(300));
		LineItem toSave = new LineItem();
		toSave.setAmount(3);
		toSave.setId(2L);
		toSave.setOrderId(ORDER);
		toSave.setProductId(PRODUCT);
		when(orderService.updateOrderTotalPrice(ID, ID,
				toSave.getAmount())).thenReturn(ORDER);
		ArgumentCaptor<LineItem> captLineItem = ArgumentCaptor.forClass(LineItem.class);
		when(lineItemRepository.saveAndFlush(captLineItem.capture())).thenReturn(toSave);
		lineItemService.saveLineItem(toSave);
		assertAll(
				() -> assertTrue(captLineItem.getValue() != null),
				() -> assertEquals(3, captLineItem.getValue().getAmount()),
				() -> assertEquals(new BigDecimal(300), captLineItem.getValue().getOrderId().getTotalPrice())
				);
	}
	
//	@Test
//	void saveLineItemIsNull() {
//		LineItem toSave = new LineItem();
//		
//		ArgumentCaptor<LineItem> captLineItem = ArgumentCaptor.forClass(LineItem.class);
//		when(lineItemRepository.saveAndFlush(captLineItem.capture())).thenReturn(toSave);
//		//updateOrderPrice(lineItem.getOrderId().getId(), lineItem.getProductId().getId(), lineItem.getAmount());
//		lineItemService.saveLineItem(toSave);
//		fail("Need to implement null check!");
//	}
	
	@Test
	void updateLineItem() {
		LineItem toSave = new LineItem();
		toSave.setAmount(3);
		toSave.setId(2L);
		toSave.setOrderId(ORDER);
		toSave.setProductId(PRODUCT);
		ArgumentCaptor<Long> captLineItem = ArgumentCaptor.forClass(Long.class);
		when(lineItemRepository.getOne(captLineItem.capture())).thenReturn(toSave);
		lineItemService.updateLineItem(ID, toSave);
		assertAll( 
				() -> assertEquals(1L, captLineItem.getValue())
				);
	}
	
	@Test
	void deleteLineItem() {
		ArgumentCaptor<Long> captLineItem = ArgumentCaptor.forClass(Long.class);
		doNothing().when(lineItemRepository).deleteById(captLineItem.capture());
		lineItemService.deleteLineItem(ID);
		assertAll( 
				() -> assertEquals(1L, captLineItem.getValue())
				);
	}

	@Test
	void getLineItemsByOrderId() {
		ArgumentCaptor<Order> captLineItem = ArgumentCaptor.forClass(Order.class);
		when(orderService.getOrderById(notNull())).thenReturn(ORDER);
		when(lineItemRepository.findByOrderId(captLineItem.capture())).thenReturn(LINEITEM_LIST);
		lineItemService.getLineItemsByOrderId(ID);
		assertAll( 
				() -> assertEquals(1L, captLineItem.getValue().getId())
				);
	}
}
