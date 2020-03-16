package com.taska.java.crickets.cricketfarm.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.mockito.MockitoAnnotations;

import com.taska.java.crickets.cricketfarm.model.BoughtItem;
import com.taska.java.crickets.cricketfarm.model.Goods;
import com.taska.java.crickets.cricketfarm.model.Purchase;
import com.taska.java.crickets.cricketfarm.repository.PurchaseRepository;

class PurchaseServiceImplTest {

	private static final Purchase PURCHASE = new Purchase();
	
	private static final List<Purchase> PURCHASE_LIST = new ArrayList<Purchase>();
	
	@Mock
	private PurchaseRepository purchaseRepository;
	
	@InjectMocks
	private PurchaseServiceImpl purchaseService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
		PURCHASE.setId(1L);
		PURCHASE.setTotalPrice(null);
		PURCHASE.setIsCompleted(false);
		PURCHASE_LIST.add(PURCHASE);
	}
	
	@Test
	void getAllPurchases() {
		when(purchaseRepository.findAll()).thenReturn(PURCHASE_LIST);
		List<Purchase> actual = purchaseService.getPurchases();
		assertFalse(actual.isEmpty());
	}

	@Test
	void getPurchase() {
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		when(purchaseRepository.getOne(captLong.capture())).thenReturn(PURCHASE);
		Purchase actual = purchaseService.getPurchase(1L);
		verify(purchaseRepository, times(1)).getOne(captLong.capture());
		assertAll(
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertTrue(actual != null)
				);
	}
	
	@Test
	void addPurchase() {
		ArgumentCaptor<Purchase> captPurchase = ArgumentCaptor.forClass(Purchase.class);
		when(purchaseRepository.saveAndFlush(captPurchase.capture()))
			.then(invocation -> invocation.getArgument(0, Purchase.class));
		Purchase actual = purchaseService.addPurchase(PURCHASE);
		verify(purchaseRepository, times(1)).saveAndFlush(captPurchase.capture());
		assertAll(
				() -> assertEquals(1L, captPurchase.getValue().getId()),
				() -> assertTrue(actual != null),
				() -> assertEquals(1L, actual.getId())
				);
	}
	
	@Test
	void updatePurchase() {
		Purchase newPurchase = new Purchase();
		newPurchase.setId(2L);
		newPurchase.setPurchaseDate(LocalDate.of(2020, 2, 2));
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Purchase> captPurchase = ArgumentCaptor.forClass(Purchase.class);
		when(purchaseRepository.getOne(captLong.capture())).thenReturn(PURCHASE);
		when(purchaseRepository.saveAndFlush(captPurchase.capture()))
			.then(invocation -> invocation.getArgument(0, Purchase.class));
		Purchase actual = purchaseService.updatePurchase(1L, newPurchase);
		verify(purchaseRepository, times(1)).getOne(captLong.capture());
		verify(purchaseRepository, times(1)).saveAndFlush(captPurchase.capture());
		assertAll(
				() -> assertTrue(actual != null),
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertEquals(1L, captPurchase.getValue().getId()),
				() -> assertEquals(1L, actual.getId()),
				() -> assertEquals(LocalDate.of(2020, 2, 2), actual.getPurchaseDate())
				);
	}
	
	@Test
	void updateCompletedPurchase() {
		PURCHASE.setIsCompleted(true);
		PURCHASE.setPurchaseDate(LocalDate.of(2020, 1, 1));
		Purchase newPurchase = new Purchase();
		newPurchase.setId(2L);
		newPurchase.setPurchaseDate(LocalDate.of(2020, 2, 2));
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
//		ArgumentCaptor<Purchase> captPurchase = ArgumentCaptor.forClass(Purchase.class);
		when(purchaseRepository.getOne(captLong.capture())).thenReturn(PURCHASE);
//		when(purchaseRepository.saveAndFlush(captPurchase.capture()))
//			.then(invocation -> invocation.getArgument(0, Purchase.class));
		Purchase actual = purchaseService.updatePurchase(1L, newPurchase);
		verify(purchaseRepository, times(1)).getOne(captLong.capture());
//		verify(purchaseRepository, times(1)).saveAndFlush(captPurchase.capture());
		assertAll(
				() -> assertTrue(actual != null),
				() -> assertEquals(1L, captLong.getValue()),
//				() -> assertEquals(1L, captPurchase.getValue().getId()),
				() -> assertEquals(1L, actual.getId()),
				() -> assertNotEquals(LocalDate.of(2020, 2, 2), actual.getPurchaseDate()),
				() -> assertEquals(LocalDate.of(2020, 1, 1), actual.getPurchaseDate())
				);
	}
	
	@Test
	void deletePurchase() {
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		doNothing().when(purchaseRepository).deleteById(captLong.capture());
		purchaseService.deletePurchase(1L);
		verify(purchaseRepository, times(1)).deleteById(captLong.capture());
		assertEquals(1L, captLong.getValue());
	}
	
	@Test
	void updateTotalPurchasePriceForEmpty() {
		Goods goods = new Goods();
		goods.setId(1L);
		goods.setName("Box");
		goods.setPrice(new BigDecimal(400));
		BoughtItem boughtItem = new BoughtItem();
		boughtItem.setId(1L);
		boughtItem.setAmount(3);
		boughtItem.setGoods(goods);
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Purchase> captPurchase = ArgumentCaptor.forClass(Purchase.class);
		when(purchaseRepository.getOne(captLong.capture())).thenReturn(PURCHASE);
		when(purchaseRepository.saveAndFlush(captPurchase.capture()))
			.then(invocation -> invocation.getArgument(0, Purchase.class));
		purchaseService.updatePurchasePrice(1L, boughtItem);
		verify(purchaseRepository,times(2)).getOne(captLong.capture());
		verify(purchaseRepository,times(1)).saveAndFlush(captPurchase.capture());
		assertAll(
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertTrue(captPurchase.getValue().getTotalPrice() != null),
				() -> assertEquals(new BigDecimal(1200), captPurchase.getValue().getTotalPrice())
				);
	}
	
	@Test
	void updateTotalPurchasePriceForZero() {
		PURCHASE.setTotalPrice(new BigDecimal(0));
		Goods goods = new Goods();
		goods.setId(1L);
		goods.setName("Box");
		goods.setPrice(new BigDecimal(400));
		BoughtItem boughtItem = new BoughtItem();
		boughtItem.setId(1L);
		boughtItem.setAmount(3);
		boughtItem.setGoods(goods);
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Purchase> captPurchase = ArgumentCaptor.forClass(Purchase.class);
		when(purchaseRepository.getOne(captLong.capture())).thenReturn(PURCHASE);
		when(purchaseRepository.saveAndFlush(captPurchase.capture()))
			.then(invocation -> invocation.getArgument(0, Purchase.class));
		purchaseService.updatePurchasePrice(1L, boughtItem);
		verify(purchaseRepository,times(2)).getOne(captLong.capture());
		verify(purchaseRepository,times(1)).saveAndFlush(captPurchase.capture());
		assertAll(
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertTrue(captPurchase.getValue().getTotalPrice() != null),
				() -> assertEquals(new BigDecimal(1200), captPurchase.getValue().getTotalPrice())
				);
	}
	
	@Test
	void updateTotalPurchasePriceForNotEmpty() {
		PURCHASE.setTotalPrice(new BigDecimal(500));
		Goods goods = new Goods();
		goods.setId(1L);
		goods.setName("Box");
		goods.setPrice(new BigDecimal(400));
		BoughtItem boughtItem = new BoughtItem();
		boughtItem.setId(1L);
		boughtItem.setAmount(3);
		boughtItem.setGoods(goods);
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Purchase> captPurchase = ArgumentCaptor.forClass(Purchase.class);
		when(purchaseRepository.getOne(captLong.capture())).thenReturn(PURCHASE);
		when(purchaseRepository.saveAndFlush(captPurchase.capture()))
			.then(invocation -> invocation.getArgument(0, Purchase.class));
		purchaseService.updatePurchasePrice(1L, boughtItem);
		verify(purchaseRepository,times(2)).getOne(captLong.capture());
		verify(purchaseRepository,times(1)).saveAndFlush(captPurchase.capture());
		assertAll(
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertTrue(captPurchase.getValue().getTotalPrice() != null),
				() -> assertEquals(new BigDecimal(1700), captPurchase.getValue().getTotalPrice())
				);
	}
	
	@Test
	void updateTotalPurchasePriceForCompletedPurchase() {
		PURCHASE.setTotalPrice(new BigDecimal(500));
		PURCHASE.setIsCompleted(true);
		Goods goods = new Goods();
		goods.setId(1L);
		goods.setName("Box");
		goods.setPrice(new BigDecimal(400));
		BoughtItem boughtItem = new BoughtItem();
		boughtItem.setId(1L);
		boughtItem.setAmount(3);
		boughtItem.setGoods(goods);
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
//		ArgumentCaptor<Purchase> captPurchase = ArgumentCaptor.forClass(Purchase.class);
		when(purchaseRepository.getOne(captLong.capture())).thenReturn(PURCHASE);
//		when(purchaseRepository.saveAndFlush(captPurchase.capture()))
//			.then(invocation -> invocation.getArgument(0, Purchase.class));
		purchaseService.updatePurchasePrice(1L, boughtItem);
		verify(purchaseRepository,times(1)).getOne(captLong.capture());
		assertAll(
				() -> assertEquals(1L, captLong.getValue())
				);
	}
	
	@Test
	void completingPurchase() {
		Purchase temp = new Purchase();
		temp.setIsCompleted(false);
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Purchase> captPurchase = ArgumentCaptor.forClass(Purchase.class);
		when(purchaseRepository.getOne(captLong.capture())).thenReturn(PURCHASE).thenReturn(temp);
		when(purchaseRepository.saveAndFlush(captPurchase.capture()))
			.then(invocation -> invocation.getArgument(0, Purchase.class));
		purchaseService.completePurchase(1L);
		verify(purchaseRepository,times(2)).getOne(captLong.capture());
		verify(purchaseRepository,times(1)).saveAndFlush(captPurchase.capture());
		assertAll(
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertEquals(true, captPurchase.getValue().getIsCompleted())
				);
	}
}
