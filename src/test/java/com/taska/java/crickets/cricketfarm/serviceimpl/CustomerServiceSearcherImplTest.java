package com.taska.java.crickets.cricketfarm.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taska.java.crickets.cricketfarm.model.Customer;
import com.taska.java.crickets.cricketfarm.model.Order;
import com.taska.java.crickets.cricketfarm.repository.CustomerRepository;


class CustomerServiceSearcherImplTest {

	private static final Long ID1 = 1L;
	private static final Long PHONE1 = 601100200L;
	private static final String NAME1 = "Jan";
	private static final String SURNAME1 = "Novak";
	private static final String EMAIL1 = "jan.novak@email.cz";
	private static final Customer CUSTOMER1 = new Customer();
	private static final Long ID2 = 2L;
	private static final Long PHONE2 = 602100200L;
	private static final String NAME2 = "Petr";
	private static final String SURNAME2 = "Francouz";
	private static final String EMAIL2 = "petr.francouz@email.cz";
	private static final Customer CUSTOMER2 = new Customer();
	private static final List<Customer> CUSTOMER_LIST = new ArrayList<Customer>();
	
	
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerServiceSearcherImpl customerServiceSearcher;
	
	@BeforeEach
	public void init() {
	    MockitoAnnotations.initMocks(this);
	    CUSTOMER1.setId(ID1);
	    CUSTOMER1.setName(NAME1);
	    CUSTOMER1.setSurname(SURNAME1);
	    CUSTOMER1.setEmail(EMAIL1);
	    CUSTOMER1.setPhoneNumber(PHONE1);
	    CUSTOMER1.setOrders(new ArrayList<Order>());
	    CUSTOMER2.setId(ID2);
	    CUSTOMER2.setName(NAME2);
	    CUSTOMER2.setSurname(SURNAME2);
	    CUSTOMER2.setEmail(EMAIL2);
	    CUSTOMER2.setPhoneNumber(PHONE2);
	    CUSTOMER2.setOrders(new ArrayList<Order>());
	    CUSTOMER_LIST.clear();
	    CUSTOMER_LIST.add(CUSTOMER1);
	    CUSTOMER_LIST.add(CUSTOMER2);
	}
	
	@Test
	public void returnAllCustomers() {
		when(customerRepository.findAll()).thenReturn(CUSTOMER_LIST);
		int actual = customerServiceSearcher.getAllCustomers().size();
		assertEquals(2, actual);
	}
	
	@Test
	public void returnCustomerById() {
		when(customerRepository.getOne((Long)notNull())).thenReturn(CUSTOMER1);
		Customer actual = customerServiceSearcher.getCustomerById(1L);
		assertEquals(CUSTOMER1, actual);
	}
	
	@Test
	public void returnCustomerThatContainsLikeStringName() {
		String likeName = "J";
		when(customerRepository.findByNameIgnoreCaseContaining((String)notNull()))
			.thenReturn(CUSTOMER_LIST.stream().filter(c -> c.getName().contains(likeName)).collect(Collectors.toList()));
		int actual = customerServiceSearcher.getCustomersLikeName(likeName).size();
		assertEquals(1, actual);
	}
	
	@Test
	public void returnCustomerThatContainsLikeStringSurname() {
		String likeSurname = "F";
		when(customerRepository.findBySurnameIgnoreCaseContaining((String)notNull()))
			.thenReturn(CUSTOMER_LIST.stream().filter(c -> c.getSurname().contains(likeSurname)).collect(Collectors.toList()));
		assertAll(
				() -> assertEquals(1, customerServiceSearcher.getCustomersLikeSurname(likeSurname).size()),
				() -> assertEquals(2L, customerServiceSearcher.getCustomersLikeSurname(likeSurname).get(0).getId())
				);
	}
	
	@Test
	public void returnCustomerByPhone() {
		when(customerRepository.findByPhoneNumberIs((Long)notNull())).thenReturn(CUSTOMER1);
		assertEquals(601100200L, customerServiceSearcher.getCustomerByPhone(PHONE1).getPhoneNumber());
	}
	
	@Test
	public void returnCustomerThatContainsLikeStringEmail() {
		String likeMail = "jan.n";
		when(customerRepository.findByEmailIgnoreCaseContaining((String)notNull()))
			.thenReturn(CUSTOMER_LIST.stream().filter(c -> c.getEmail().contains(likeMail)).collect(Collectors.toList()));
		assertAll(
				() -> assertEquals(1, customerServiceSearcher.getCustomersLikeMail(likeMail).size()),
				() -> assertEquals(1L, customerServiceSearcher.getCustomersLikeMail(likeMail).get(0).getId())
				);
	}

}
