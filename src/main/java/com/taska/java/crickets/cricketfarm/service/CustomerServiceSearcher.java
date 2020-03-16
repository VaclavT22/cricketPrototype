package com.taska.java.crickets.cricketfarm.service;

import java.util.List;

import com.taska.java.crickets.cricketfarm.model.Customer;

public interface CustomerServiceSearcher {

	List<Customer> getAllCustomers();
	Customer getCustomerById(Long id);
	List<Customer> getCustomersLikeName(String nameContains);
	List<Customer> getCustomersLikeSurname(String surnameContains);
	Customer getCustomerByPhone(Long phoneNumber);
	List<Customer> getCustomersLikeMail(String customerMail);
}
