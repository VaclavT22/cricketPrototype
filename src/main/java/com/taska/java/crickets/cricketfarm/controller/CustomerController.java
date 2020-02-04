package com.taska.java.crickets.cricketfarm.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taska.java.crickets.cricketfarm.model.Customer;
import com.taska.java.crickets.cricketfarm.repository.CustomerRepository;
import com.taska.java.crickets.cricketfarm.utils.NumberVerifier;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private NumberVerifier numberVerifier;
	
	@GetMapping
	public List<Customer> getCustomers(){
		return customerRepository.findAll();
	}
	
	@PostMapping
	public Customer createCustomer(@RequestBody final Customer customer) {
		return customerRepository.saveAndFlush(customer);
	}
	
	@GetMapping("/{id}")
	public Customer getCustomer(@PathVariable("id") Long id) {
		return customerRepository.getOne(id);
	}
	
	@PutMapping("/{id}")
	public Customer updateCustomer(@RequestBody final Customer customer, @PathVariable("id") Long id) {
		Customer existingCustomer = customerRepository.getOne(id);
		BeanUtils.copyProperties(customer, existingCustomer, "id");
		return customerRepository.saveAndFlush(existingCustomer);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable("id") Long id) {
		customerRepository.deleteById(id);
	}

	@GetMapping("/likeName")
	public List<Customer> getCustomersLikeName(@RequestParam String customerName) {
		return customerRepository.findByNameIgnoreCaseContaining(customerName);
	}
	
	@GetMapping("/likeSurname")
	public List<Customer> getCustomersLikeSurname(@RequestParam String customerSurname) {
		return customerRepository.findBySurnameIgnoreCaseContaining(customerSurname);
	}
	
	@GetMapping("/likePhone")
	public Customer getCustomerByPhone(@RequestParam(name = "customerPhone") String customerPhoneString) {
		Long customerPhoneNumber = 0L;
		if(numberVerifier.isAbleToParseLong(customerPhoneString)) {
			customerPhoneNumber = Long.parseLong(customerPhoneString);
		}
		return customerRepository.findByPhoneNumberIs(customerPhoneNumber);
	}
	
	@GetMapping("/likeMail")
	public List<Customer> getCustomersLikeMail(@RequestParam String customerMail) {
		return customerRepository.findByEmailIgnoreCaseContaining(customerMail);
	}
}
