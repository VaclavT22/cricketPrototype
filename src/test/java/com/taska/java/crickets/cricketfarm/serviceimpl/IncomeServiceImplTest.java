package com.taska.java.crickets.cricketfarm.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
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

import com.taska.java.crickets.cricketfarm.model.Customer;
import com.taska.java.crickets.cricketfarm.model.Income;
import com.taska.java.crickets.cricketfarm.model.Order;
import com.taska.java.crickets.cricketfarm.repository.IncomeRepository;

class IncomeServiceImplTest {

	private static final List<Income> INCOME_LIST = new ArrayList<Income>();
	private static final Income INCOME = new Income();
	private static final Order ORDER = new Order();
	private static final Customer CUSTOMER = new Customer();
	private static final LocalDate DATE = LocalDate.of(2020, 2, 2);
	
	@Mock
	private IncomeRepository incomeRepository;
	
	@InjectMocks
	private IncomeServiceImpl incomeService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
		ORDER.setCustomer(CUSTOMER);
		ORDER.setDeliveryDate(DATE);
		ORDER.setCustomer(CUSTOMER);
		ORDER.setId(1L);
		ORDER.setIsCompleted(true);
		ORDER.setIsDelivered(true);
		ORDER.setDeliveryDate(DATE);
		ORDER.setTotalPrice(new BigDecimal(1000));
		INCOME.setId(1L);
		INCOME.setIncomeOrder(ORDER);
		INCOME_LIST.clear();
		INCOME_LIST.add(INCOME);
	}
	
	@Test
	void getAllIncomes() {
		when(incomeRepository.findAll()).thenReturn(INCOME_LIST);
		List<Income> actual = incomeService.getIncomes();
		assertFalse(actual.isEmpty());
	}
	
	@Test
	void getIncomeById() {
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		when(incomeRepository.getOne(captLong.capture())).thenReturn(INCOME);
		Income actual = incomeService.getIncome(1L);
		verify(incomeRepository, times(1)).getOne(captLong.capture());
		assertAll(
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertTrue(actual != null)
				);
	}
	
	@Test
	void generateIncomeForDeliveredOrder() {
		ArgumentCaptor<Income> captIncome = ArgumentCaptor.forClass(Income.class);
		when(incomeRepository.saveAndFlush(captIncome.capture())).then(i -> i.getArgument(0, Income.class));
		Income actual = incomeService.generateIncome(ORDER);
		verify(incomeRepository, times(1)).saveAndFlush(captIncome.capture());
		assertAll(
				() -> assertEquals(new BigDecimal(1000), actual.getIncomeValue()),
				() -> assertTrue(actual != null)
				);
	}

//	@Test
//	void notGenerateIncomeForUndeliveredOrder() {
//		ORDER.setIsDelivered(false);
//		ArgumentCaptor<Income> captIncome = ArgumentCaptor.forClass(Income.class);
//		when(incomeRepository.saveAndFlush(captIncome.capture())).then(i -> i);
//		Income actual = incomeService.generateIncome(ORDER);
//		verify(incomeRepository, times(0)).saveAndFlush(captIncome.capture());
//		assertAll(
//    			() -> assertEquals(null, actual.getIncomeValue())
////				() -> assertTrue(actual == null)
//				);
//	}
}
