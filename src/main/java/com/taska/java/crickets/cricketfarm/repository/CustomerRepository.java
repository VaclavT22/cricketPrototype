package com.taska.java.crickets.cricketfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taska.java.crickets.cricketfarm.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
