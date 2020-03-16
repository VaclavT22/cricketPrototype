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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.taska.java.crickets.cricketfarm.model.Order;
import com.taska.java.crickets.cricketfarm.model.Product;
import com.taska.java.crickets.cricketfarm.repository.OrderRepository;
import com.taska.java.crickets.cricketfarm.service.ProductService;
import com.taska.java.crickets.cricketfarm.utils.DateVerifier;

class OrderServiceImplTest {

	private static final Order ORDER = new Order();
	private static final List<Order> ORDER_LIST = new ArrayList<Order>();
	
	
	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private DateVerifier dateVerifier;
	
	@Mock
	private ProductService productService;
	
	@InjectMocks
	private OrderServiceImpl orderService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
		ORDER.setId(1L);
		ORDER.setOrderDate(LocalDate.of(2020, 1, 4));
		ORDER.setIsCompleted(false);
		ORDER.setIsDelivered(false);
		ORDER_LIST.add(ORDER);
	}
	
	
	@Test
	void getOrders() {
		when(orderRepository.findAll()).thenReturn(ORDER_LIST);
		List<Order> tempList = orderService.getOrders();
		assertFalse(tempList.isEmpty());
	}
	
	@Test
	void getOrderById() {
		ArgumentCaptor<Long> captOrder = ArgumentCaptor.forClass(Long.class);
		when(orderRepository.getOne(captOrder.capture())).thenReturn(ORDER);
		Order temp = orderRepository.getOne(1L);
		assertAll( 
				() -> assertEquals(1L, captOrder.getValue()),
				() -> assertFalse(temp == null)
				);
	}
	
	@Test
	void saveOrder() {
		ArgumentCaptor<Order> captOrder = ArgumentCaptor.forClass(Order.class);
		when(orderRepository.saveAndFlush(captOrder.capture())).thenReturn(ORDER);
		Order temp = orderService.saveOrder(ORDER);
		assertAll( 
				() -> assertFalse(captOrder.getValue().getIsCompleted()),
				() -> assertFalse(captOrder.getValue().getIsDelivered()),
				() -> assertFalse(temp == null)
				);
	}
	
	@Test
	void updateOrder() {
		Order temp = new Order();
		temp.setOrderDate(LocalDate.of(2020, 1, 6));
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		//ArgumentCaptor<Order> captOrder = ArgumentCaptor.forClass(Order.class);
		when(orderRepository.getOne(captLong.capture())).thenReturn(ORDER);
//		when(orderRepository.getOne(Mockito.anyLong())).then(inv -> {
//			System.out.println(inv.getArgument(0, Long.class));
//			return inv.getArgument(0, Long.class);
//		});
		when(orderRepository.saveAndFlush(Mockito.any(Order.class)))
			.then(i -> i.getArgument(0, Order.class));
		Order actual = orderService.updateOrder(1L, temp);
		assertAll( 
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertEquals(1L, actual.getId()),
				() -> assertEquals(LocalDate.of(2020, 1, 6), actual.getOrderDate())
 				);
	}
	
	@Test
	void deleteOrder() {
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		doNothing().when(orderRepository).deleteById(captLong.capture());
		orderService.deleteOrder(1L);
		assertEquals(1L, captLong.getValue());
	}
	
	@Test
	void completeOrder() {
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Order> captOrder = ArgumentCaptor.forClass(Order.class);
		when(orderRepository.getOne(captLong.capture())).thenReturn(ORDER);
		when(orderRepository.saveAndFlush(captOrder.capture()))
			.then(invocation -> invocation.getArgument(0, Order.class));
		Order actual = orderService.completeOrder(1L);
		verify(orderRepository, times(2)).getOne(captLong.capture());
		verify(orderRepository, times(1)).saveAndFlush(captOrder.capture());
		assertAll(
				() -> assertEquals(1L, actual.getId()),
				() -> assertTrue(actual.getIsCompleted())
				);
	}
	
	
	@Test
	void deliverOrder() {
		ORDER.setIsCompleted(true);
		ArgumentCaptor<String> captDate = ArgumentCaptor.forClass(String.class);
		when(orderRepository.getOne(Mockito.anyLong())).thenReturn(ORDER);
		when(orderRepository.saveAndFlush(Mockito.any(Order.class)))
			.then(invocation -> invocation.getArgument(0, Order.class));
		when(dateVerifier.isDateCorrect(captDate.capture())).thenReturn(true);
		Order actual = orderService.deliverOrder(1L, "2020-01-01");
		Mockito.verify(dateVerifier, times(1)).isDateCorrect(captDate.capture());
		assertAll(
				() -> assertTrue(actual.getIsDelivered()),
				() -> assertEquals(LocalDate.of(2020, 1, 1), actual.getDeliveryDate())
				);
		
	}
	
	@Test
	void deliverOrderWhenCompleteIsNotDone() {
		ArgumentCaptor<Order> captOrder = ArgumentCaptor.forClass(Order.class);
		when(orderRepository.getOne(Mockito.anyLong())).thenReturn(ORDER);
		when(orderRepository.saveAndFlush(captOrder.capture()))
			.then(invocation -> invocation.getArgument(0, Order.class));
		Order actual = orderService.deliverOrder(1L, "2020-01-01");
		Mockito.verify(orderRepository, times(0)).saveAndFlush(captOrder.capture());
		assertAll(
				() -> assertFalse(actual.getIsDelivered()),
				() -> assertEquals(null, actual.getDeliveryDate())
				);
	}
	
	@Test
	void updateOrderTotalPrice() {
		Product temp = new Product();
		temp.setPrice(new BigDecimal(450));
		ArgumentCaptor<Order> captOrder = ArgumentCaptor.forClass(Order.class);
		ArgumentCaptor<Long> captProduct = ArgumentCaptor.forClass(Long.class);
		when(orderRepository.getOne(Mockito.anyLong())).thenReturn(ORDER);
		when(productService.getProductById(captProduct.capture())).thenReturn(temp);
		when(orderRepository.saveAndFlush(captOrder.capture()))
			.then(invocation -> invocation.getArgument(0, Order.class));
		Order actual = orderService.updateOrderTotalPrice(1L, 1L, 1);
		Mockito.verify(orderRepository, times(1)).saveAndFlush(captOrder.capture());
		Mockito.verify(productService, times(1)).getProductById(captProduct.capture());
		assertAll(
				() -> assertEquals(new BigDecimal(450), actual.getTotalPrice())
				);
	}

}
