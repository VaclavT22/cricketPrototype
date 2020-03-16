package com.taska.java.crickets.cricketfarm.service;

import java.util.List;

import com.taska.java.crickets.cricketfarm.model.Income;
import com.taska.java.crickets.cricketfarm.model.Order;

public interface IncomeService {
	public List<Income> getIncomes();
	public Income getIncome(Long id);
	public Income generateIncome(Order order);
}
