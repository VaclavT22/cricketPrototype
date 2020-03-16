package com.taska.java.crickets.cricketfarm.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taska.java.crickets.cricketfarm.model.Goods;
import com.taska.java.crickets.cricketfarm.repository.GoodsRepository;

class GoodsServiceImplTest {

	private static final Goods GOODS = new Goods();
	private static final List<Goods> GOODS_LIST = new ArrayList<Goods>();
	
	@Mock
	private GoodsRepository goodsRepository;
	
	@InjectMocks
	private GoodsServiceImpl goodsService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
		GOODS.setId(1L);
		GOODS.setName("Food");
		GOODS.setPrice(new BigDecimal(75));
		GOODS_LIST.clear();
		GOODS_LIST.add(GOODS);
	}
	
	@Test
	void getAllGoods() {
		when(goodsRepository.findAll()).thenReturn(GOODS_LIST);
		List<Goods> actual = goodsService.getAllGoods();
		assertTrue(actual != null);
	}
	
	@Test
	void getGoods() {
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		when(goodsRepository.getOne(captLong.capture())).thenReturn(GOODS);
		Goods actual = goodsService.getGoods(1L);
		verify(goodsRepository, times(1)).getOne(captLong.capture());
		assertAll(
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertTrue(actual != null)
				);
	}

	@Test
	void saveGoods() {
		ArgumentCaptor<Goods> captGood = ArgumentCaptor.forClass(Goods.class);
		when(goodsRepository.saveAndFlush(captGood.capture())).then(i -> i.getArgument(0, Goods.class));
		Goods actual = goodsService.addGoods(GOODS);
		verify(goodsRepository, times(1)).saveAndFlush(captGood.capture());
		assertAll(
				() -> assertTrue(actual != null),
				() -> assertEquals("Food", actual.getName()),
				() -> assertEquals("Food", captGood.getValue().getName())
				);
	}
	
	@Test
	void updateGoods() {
		Goods good = new Goods();
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Goods> captGoods = ArgumentCaptor.forClass(Goods.class);
		when(goodsRepository.getOne(captLong.capture())).thenReturn(GOODS);
		when(goodsRepository.saveAndFlush(captGoods.capture())).then(i -> i.getArgument(0, Goods.class));
		Goods actual = goodsService.updateGoods(1L, good);
		verify(goodsRepository, times(1)).getOne(captLong.capture());
		verify(goodsRepository, times(1)).saveAndFlush(captGoods.capture());
		assertAll(
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertEquals(1L, captGoods.getValue().getId()),
				() -> assertTrue(actual != null),
				() -> assertEquals(1L, actual.getId())
				);
	}
	
	@Test
	void deleteGoods() {
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		doNothing().when(goodsRepository).deleteById(captLong.capture());
		goodsService.deleteGoods(1L);
		verify(goodsRepository, times(1)).deleteById(captLong.capture());
		assertEquals(1L, captLong.getValue());
	}
}
