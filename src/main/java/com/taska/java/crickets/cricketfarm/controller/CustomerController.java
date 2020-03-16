package com.taska.java.crickets.cricketfarm.controller;

import java.util.List;

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
import com.taska.java.crickets.cricketfarm.service.CustomerServiceModifier;
import com.taska.java.crickets.cricketfarm.service.CustomerServiceSearcher;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
	
	@Autowired
	private CustomerServiceModifier customerServiceModifier;
	
	@Autowired
	private CustomerServiceSearcher customerServiceSearcher;
	
	
	@GetMapping
	public List<Customer> getCustomers(){
		return customerServiceSearcher.getAllCustomers();
	}
	
	@PostMapping
	public Customer addCustomer(@RequestBody final Customer customer) {
		return customerServiceModifier.saveCustomer(customer);
	}
	
	@GetMapping("/{id}")
	public Customer getCustomer(@PathVariable("id") Long id) {
		return customerServiceSearcher.getCustomerById(id);
	}
	
	@PutMapping("/{id}")
	public Customer updateCustomer(@RequestBody final Customer customer, @PathVariable("id") Long id) {
		return customerServiceModifier.updateCustomer(id, customer);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable("id") Long id) {
		customerServiceModifier.deleteCustomer(id);
	}

	@GetMapping("/likeName")
	public List<Customer> getCustomersLikeName(@RequestParam String customerName) {
		return customerServiceSearcher.getCustomersLikeName(customerName);
	}
	
	@GetMapping("/likeSurname")
	public List<Customer> getCustomersLikeSurname(@RequestParam String customerSurname) {
		return customerServiceSearcher.getCustomersLikeSurname(customerSurname);
	}
	
	@GetMapping("/likePhone")
	public Customer getCustomerByPhone(@RequestParam(name = "customerPhone") Long customerPhoneString) {
		return customerServiceSearcher.getCustomerByPhone(customerPhoneString);
	}
	
	@GetMapping("/likeMail")
	public List<Customer> getCustomersLikeMail(@RequestParam String customerMail) {
		return customerServiceSearcher.getCustomersLikeMail(customerMail);
	}
}
