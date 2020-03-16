package com.taska.java.crickets.cricketfarm.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taska.java.crickets.cricketfarm.model.Customer;
import com.taska.java.crickets.cricketfarm.repository.CustomerRepository;
import com.taska.java.crickets.cricketfarm.service.CustomerServiceSearcher;

@Service
public class CustomerServiceSearcherImpl implements CustomerServiceSearcher {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getCustomerById(Long id) {
		return customerRepository.getOne(id);
	}

	@Override
	public List<Customer> getCustomersLikeName(String nameContains) {
		return customerRepository.findByNameIgnoreCaseContaining(nameContains);
	}

	@Override
	public List<Customer> getCustomersLikeSurname(String surnameContains) {
		return customerRepository.findBySurnameIgnoreCaseContaining(surnameContains);
	}

	@Override
	public Customer getCustomerByPhone(Long phoneNumber) {
		
		return customerRepository.findByPhoneNumberIs(phoneNumber);
	}

	@Override
	public List<Customer> getCustomersLikeMail(String mailContains) {
		return customerRepository.findByEmailIgnoreCaseContaining(mailContains);
	}

}
