package com.taska.java.crickets.cricketfarm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taska.java.crickets.cricketfarm.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByNameIgnoreCaseContaining(String customerName);
	List<Customer> findBySurnameIgnoreCaseContaining(String customerSurname);
	Customer findByPhoneNumberIs(Long customerPhoneNumber);
	List<Customer> findByEmailIgnoreCaseContaining(String customerMail);
}
