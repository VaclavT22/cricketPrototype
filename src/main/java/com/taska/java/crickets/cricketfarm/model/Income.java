package com.taska.java.crickets.cricketfarm.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Income {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate incomeDate;
	private BigDecimal incomeValue;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer incomeProducer;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Order incomeOrder;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getIncomeDate() {
		return incomeDate;
	}
	public void setIncomeDate(LocalDate incomeDate) {
		this.incomeDate = incomeDate;
	}
	public BigDecimal getIncomeValue() {
		return incomeValue;
	}
	public void setIncomeValue(BigDecimal incomeValue) {
		this.incomeValue = incomeValue;
	}
	public Customer getIncomeProducer() {
		return incomeProducer;
	}
	public void setIncomeProducer(Customer incomeProducer) {
		this.incomeProducer = incomeProducer;
	}
	public Order getIncomeOrder() {
		return incomeOrder;
	}
	public void setIncomeOrder(Order incomeOrder) {
		this.incomeOrder = incomeOrder;
	}
}
