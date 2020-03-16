package com.taska.java.crickets.cricketfarm.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.taska.java.crickets.cricketfarm.repository.BoughtItemRepository;

class BoughtItemServiceImplTest {

	private static final BoughtItem BOUGHT_ITEM = new BoughtItem();
	private static final Goods GOODS = new Goods();
	private static final Purchase PURCHASE = new Purchase();
	private static final List<BoughtItem> BOUGHT_ITEM_LIST = new ArrayList<BoughtItem>();
	
	@Mock
	private BoughtItemRepository boughtItemRepository;
	
	@InjectMocks
	private BoughtItemServiceImpl boughtItemService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
		BOUGHT_ITEM.setId(1L);
		BOUGHT_ITEM.setAmount(2);
		BOUGHT_ITEM.setGoods(GOODS);
		BOUGHT_ITEM.setPurchase(PURCHASE);
		BOUGHT_ITEM_LIST.add(BOUGHT_ITEM);
	}
	
	@Test
	void getAllBoughtItems() {
		when(boughtItemRepository.findAll()).thenReturn(BOUGHT_ITEM_LIST);
		List<BoughtItem> actual = boughtItemService.getBoughtItems();
		assertTrue(actual != null);
	}

	@Test
	void getBoughtItem() {
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		when(boughtItemRepository.getOne(captLong.capture())).thenReturn(BOUGHT_ITEM);
		BoughtItem actual = boughtItemService.getBoughtItem(1L);
		verify(boughtItemRepository, times(1)).getOne(captLong.capture());
		assertAll(
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertTrue(actual != null)
				);
	}
	
	@Test
	void addBoughtItem() {
		ArgumentCaptor<BoughtItem> captBoughtItem = ArgumentCaptor.forClass(BoughtItem.class);
		when(boughtItemRepository.saveAndFlush(captBoughtItem.capture()))
			.then(invocation -> invocation.getArgument(0, BoughtItem.class));
		BoughtItem actual = boughtItemService.addBoughtItem(BOUGHT_ITEM);
		verify(boughtItemRepository,times(1)).saveAndFlush(captBoughtItem.capture());
		assertAll(
				() -> assertTrue(actual != null),
				() -> assertEquals(1L, actual.getId()),
				() -> assertEquals(1L, captBoughtItem.getValue().getId())
				);
	}
	
	@Test
	void updateBoughtItem() {
		BoughtItem boughtItem = new BoughtItem();
		boughtItem.setAmount(4);
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<BoughtItem> captBoughtItem = ArgumentCaptor.forClass(BoughtItem.class);
		when(boughtItemRepository.getOne(captLong.capture())).thenReturn(BOUGHT_ITEM);
		when(boughtItemRepository.saveAndFlush(captBoughtItem.capture()))
			.then(invocation -> invocation.getArgument(0, BoughtItem.class));
		BoughtItem actual = boughtItemService.updateBoughtItem(1L, boughtItem);
		verify(boughtItemRepository,times(1)).getOne(captLong.capture());
		verify(boughtItemRepository,times(1)).saveAndFlush(captBoughtItem.capture());
		assertAll(
				() -> assertTrue(actual != null),
				() -> assertEquals(1L, actual.getId()),
				() -> assertEquals(4, actual.getAmount()),
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertEquals(1L, captBoughtItem.getValue().getId())
				);
	}
	
	@Test
	void updateBoughtItemUpdatorHasDifferentId() {
		BoughtItem boughtItem = new BoughtItem();
		boughtItem.setAmount(4);
		boughtItem.setId(2L);
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<BoughtItem> captBoughtItem = ArgumentCaptor.forClass(BoughtItem.class);
		when(boughtItemRepository.getOne(captLong.capture())).thenReturn(BOUGHT_ITEM);
		when(boughtItemRepository.saveAndFlush(captBoughtItem.capture()))
			.then(invocation -> invocation.getArgument(0, BoughtItem.class));
		BoughtItem actual = boughtItemService.updateBoughtItem(1L, boughtItem);
		verify(boughtItemRepository,times(1)).getOne(captLong.capture());
		verify(boughtItemRepository,times(1)).saveAndFlush(captBoughtItem.capture());
		assertAll(
				() -> assertTrue(actual != null),
				() -> assertEquals(1L, actual.getId()),
				() -> assertEquals(4, actual.getAmount()),
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertEquals(1L, captBoughtItem.getValue().getId())
				);
	}
	
	@Test
	void deleteBoughtItem() {
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		doNothing().when(boughtItemRepository).deleteById(captLong.capture());
		boughtItemService.deleteBoughtItem(1L);
		verify(boughtItemRepository, times(1)).deleteById(captLong.capture());
		assertEquals(1L, captLong.getValue());
	}
}
