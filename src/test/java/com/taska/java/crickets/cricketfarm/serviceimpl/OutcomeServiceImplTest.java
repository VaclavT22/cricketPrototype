package com.taska.java.crickets.cricketfarm.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.taska.java.crickets.cricketfarm.model.Outcome;
import com.taska.java.crickets.cricketfarm.model.Purchase;
import com.taska.java.crickets.cricketfarm.repository.OutcomeRepository;

class OutcomeServiceImplTest {

	private static final Outcome OUTCOME = new Outcome();
	private static final List<Outcome> OUTCOME_LIST = new ArrayList<Outcome>();
	
	@Mock
	private OutcomeRepository outcomeRepository;
	
	@InjectMocks
	private OutcomeServiceImpl outcomeService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
		OUTCOME.setId(1L);
		OUTCOME_LIST.add(OUTCOME);
	}
	
	@Test
	void getAllOutcomes() {
		when(outcomeRepository.findAll()).thenReturn(OUTCOME_LIST);
		List<Outcome> actual = outcomeService.getOutcomes();
		assertFalse(actual.isEmpty());
	}
	
	@Test
	void getOutcome() {
		ArgumentCaptor<Long> captLong = ArgumentCaptor.forClass(Long.class);
		when(outcomeRepository.getOne(captLong.capture())).thenReturn(OUTCOME);
		Outcome actual = outcomeService.getOutcome(1L);
		verify(outcomeRepository, times(1)).getOne(captLong.capture());
		assertAll(
				() -> assertEquals(1L, captLong.getValue()),
				() -> assertTrue(actual != null)
				);
	}
	
	@Test
	void generateOutcome() {
		Purchase purchase = new Purchase();
		purchase.setPurchaseDate(LocalDate.of(2020, 2, 2));
		purchase.setTotalPrice(new BigDecimal(2000));
		ArgumentCaptor<Outcome> captOutcome = ArgumentCaptor.forClass(Outcome.class);
		when(outcomeRepository.
				saveAndFlush(captOutcome.capture())).then(invocation -> invocation.getArgument(0, Outcome.class));
		outcomeService.generateOutcome(purchase);
		verify(outcomeRepository, times(1)).saveAndFlush(captOutcome.capture());
		assertAll(
				() -> assertTrue(captOutcome.getValue() != null),
				() -> assertEquals(LocalDate.of(2020, 2, 2), captOutcome.getValue().getBoughtDate()),
				() -> assertEquals(new BigDecimal(2000), captOutcome.getValue().getTotalPrice())
				);
	}

}
