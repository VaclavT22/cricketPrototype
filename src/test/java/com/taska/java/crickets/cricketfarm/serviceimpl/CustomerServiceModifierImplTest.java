package com.taska.java.crickets.cricketfarm.serviceimpl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taska.java.crickets.cricketfarm.model.Customer;
import com.taska.java.crickets.cricketfarm.model.Order;
import com.taska.java.crickets.cricketfarm.repository.CustomerRepository;



class CustomerServiceModifierImplTest {
	
	private static final Long ID1 = 1L;
	private static final Long PHONE1 = 601100200L;
	private static final String NAME1 = "Jan";
	private static final String SURNAME1 = "Novak";
	private static final String EMAIL1 = "jan.novak@email.cz";
	private static final Customer CUSTOMER1 = new Customer();
	private static final List<Customer> CUSTOMER_LIST = new ArrayList<Customer>();
	
	
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerServiceModifierImpl customerServiceModifier;
	
	@BeforeEach
	public void init() {
	    MockitoAnnotations.initMocks(this);
	    CUSTOMER1.setId(ID1);
	    CUSTOMER1.setName(NAME1);
	    CUSTOMER1.setSurname(SURNAME1);
	    CUSTOMER1.setEmail(EMAIL1);
	    CUSTOMER1.setPhoneNumber(PHONE1);
	    CUSTOMER1.setOrders(new ArrayList<Order>());
	    CUSTOMER_LIST.clear();
	    CUSTOMER_LIST.add(CUSTOMER1);
	}
	
	@Test
	public void saveNewCustomer() {
		when(customerRepository.saveAndFlush((Customer)notNull())).thenReturn(CUSTOMER1);
		Customer actual = customerServiceModifier.saveCustomer(CUSTOMER1);
		assertEquals(CUSTOMER1, actual);
	}
	
	@Test
	public void modifyCustomer() {
		Customer cust = new Customer();
		cust.setName("Petr");
		cust.setEmail(EMAIL1);
		cust.setPhoneNumber(PHONE1);
		cust.setSurname(SURNAME1);
		when(customerRepository.getOne((Long)notNull())).thenReturn(CUSTOMER1);
		ArgumentCaptor<Customer> captCustomer = ArgumentCaptor.forClass(Customer.class);
		when(customerRepository.saveAndFlush(captCustomer.capture())).thenReturn(cust);
		
		customerServiceModifier.updateCustomer(1L, cust);
		
		assertAll( 
				() -> assertEquals("Petr", captCustomer.getValue().getName()),
				() -> assertEquals(1L, captCustomer.getValue().getId()),
				() -> assertEquals("Novak", captCustomer.getValue().getSurname())
				);
	}
	
	@Test 
	public void deleteCustomer() {
		ArgumentCaptor<Long> captCustomerLong = ArgumentCaptor.forClass(Long.class);
		doNothing().when(customerRepository).deleteById(captCustomerLong.capture());
		customerServiceModifier.deleteCustomer(1L);
		assertEquals(1L, captCustomerLong.getValue());
	}

}
