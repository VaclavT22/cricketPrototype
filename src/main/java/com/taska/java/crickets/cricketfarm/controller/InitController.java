package com.taska.java.crickets.cricketfarm.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taska.java.crickets.cricketfarm.model.Customer;
import com.taska.java.crickets.cricketfarm.model.Product;
import com.taska.java.crickets.cricketfarm.repository.CustomerRepository;
import com.taska.java.crickets.cricketfarm.repository.ProductRepository;

@RestController
@RequestMapping("/init")
public class InitController {

	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping
	public String initData() {
		customerRepository.saveAll(getInitCustomers());
		productRepository.saveAll(getInitProducts());
		return "Data was initialized!";
	}
	
	private List<Customer> getInitCustomers() {
		Customer cust1 = new Customer();
		cust1.setName("Jan");
		cust1.setSurname("Novak");
		cust1.setEmail("jan.novak@email.cz");
		cust1.setPhoneNumber(601100200L);
		Customer cust2 = new Customer();
		cust2.setName("Petr");
		cust2.setSurname("Klada");
		cust2.setEmail("petr.klada@email.cz");
		cust2.setPhoneNumber(601100300L);
		Customer cust3 = new Customer();
		cust3.setName("Karel");
		cust3.setSurname("Brzek");
		cust3.setEmail("karel.brzek@email.cz");
		cust3.setPhoneNumber(601100400L);
		Customer cust4 = new Customer();
		cust4.setName("Jarmila");
		cust4.setSurname("Janatova");
		cust4.setEmail("jarmila.janatova@email.cz");
		cust4.setPhoneNumber(601100500L);
		Customer cust5 = new Customer();
		cust5.setName("Lucie");
		cust5.setSurname("Mala");
		cust5.setEmail("lucie.mala@email.cz");
		cust5.setPhoneNumber(601100600L);
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(cust1);
		customers.add(cust2);
		customers.add(cust3);
		customers.add(cust4);
		customers.add(cust5);
		return customers;
	}
	
	private List<Product> getInitProducts(){
		Product product1 = new Product();
		product1.setName("Cvrcek Domaci");
		product1.setAge("Dospely");
		product1.setPackaging("Krabice");
		product1.setVolume(1f);
		product1.setPrice(new BigDecimal(450));
		Product product2 = new Product();
		product2.setName("Cvrcek Domaci");
		product2.setAge("Stredni");
		product2.setPackaging("Krabice");
		product2.setVolume(1f);
		product2.setPrice(new BigDecimal(450));
		Product product3 = new Product();
		product3.setName("Cvrcek Domaci");
		product3.setAge("Maly");
		product3.setPackaging("Krabice");
		product3.setVolume(1f);
		product3.setPrice(new BigDecimal(550));
		Product product4 = new Product();
		product4.setName("Cvrcek Domaci");
		product4.setAge("Dospely");
		product4.setPackaging("Krabice");
		product4.setVolume(0.5f);
		product4.setPrice(new BigDecimal(300));
		Product product5 = new Product();
		product5.setName("Cvrcek Domaci");
		product5.setAge("Stredni");
		product5.setPackaging("Krabice");
		product5.setVolume(0.5f);
		product5.setPrice(new BigDecimal(300));
		Product product6 = new Product();
		product6.setName("Cvrcek Domaci");
		product6.setAge("Maly");
		product6.setPackaging("Krabice");
		product6.setVolume(0.5f);
		product6.setPrice(new BigDecimal(400));
		Product product7 = new Product();
		product7.setName("Cvrcek Domaci");
		product7.setAge("Dospely");
		product7.setPackaging("Box");
		product7.setVolume(0.25f);
		product7.setPrice(new BigDecimal(200));
		Product product8 = new Product();
		product8.setName("Cvrcek Domaci");
		product8.setAge("Stredni");
		product8.setPackaging("Box");
		product8.setVolume(0.25f);
		product8.setPrice(new BigDecimal(200));
		Product product9 = new Product();
		product9.setName("Cvrcek Domaci");
		product9.setAge("Maly");
		product9.setPackaging("Box");
		product9.setVolume(0.25f);
		product9.setPrice(new BigDecimal(250));
		Product product10 = new Product();
		product10.setName("Cvrcek Domaci");
		product10.setAge("Dospely");
		product10.setPackaging("Krabicka");
		product10.setVolume(0.1f);
		product10.setPrice(new BigDecimal(85));
		Product product11 = new Product();
		product11.setName("Cvrcek Domaci");
		product11.setAge("Stredni");
		product11.setPackaging("Krabicka");
		product11.setVolume(0.1f);
		product11.setPrice(new BigDecimal(85));
		Product product12 = new Product();
		product12.setName("Cvrcek Domaci");
		product12.setAge("Maly");
		product12.setPackaging("Krabicka");
		product12.setVolume(0.1f);
		product12.setPrice(new BigDecimal(95));
		Product product13 = new Product();
		product13.setName("Cvrcek Domaci");
		product13.setAge("Mikro");
		product13.setPackaging("Krabicka");
		product13.setVolume(0.1f);
		product13.setPrice(new BigDecimal(110));
		List<Product> products = new ArrayList<Product>();
		products.add(product1);
		products.add(product2);
		products.add(product3);
		products.add(product4);
		products.add(product5);
		products.add(product6);
		products.add(product7);
		products.add(product8);
		products.add(product9);
		products.add(product10);
		products.add(product11);
		products.add(product12);
		products.add(product13);
		return products;
	}
}
