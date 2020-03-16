package com.taska.java.crickets.cricketfarm.service;

import com.taska.java.crickets.cricketfarm.model.Customer;

public interface CustomerServiceModifier {

	Customer saveCustomer(Customer customer);
	Customer updateCustomer(Long id, Customer customer);
	void deleteCustomer(Long id);
	
}
