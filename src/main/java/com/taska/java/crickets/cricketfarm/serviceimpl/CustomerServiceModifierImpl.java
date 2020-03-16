package com.taska.java.crickets.cricketfarm.serviceimpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taska.java.crickets.cricketfarm.model.Customer;
import com.taska.java.crickets.cricketfarm.repository.CustomerRepository;
import com.taska.java.crickets.cricketfarm.service.CustomerServiceModifier;

@Service
public class CustomerServiceModifierImpl implements CustomerServiceModifier {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepository.saveAndFlush(customer);
	}

	@Override
	public Customer updateCustomer(Long id, Customer customer) {
		Customer existingCustomer = customerRepository.getOne(id);
		BeanUtils.copyProperties(customer, existingCustomer, "id");
		return customerRepository.saveAndFlush(existingCustomer);
	}

	@Override
	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}
}
