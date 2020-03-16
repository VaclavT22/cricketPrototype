package com.taska.java.crickets.cricketfarm.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taska.java.crickets.cricketfarm.model.Income;
import com.taska.java.crickets.cricketfarm.model.Order;
import com.taska.java.crickets.cricketfarm.repository.IncomeRepository;
import com.taska.java.crickets.cricketfarm.service.IncomeService;

@Service
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	private IncomeRepository incomeRepository;

	
	@Override
	public List<Income> getIncomes() {
		return incomeRepository.findAll();
	}

	@Override
	public Income getIncome(Long id) {
		return incomeRepository.getOne(id);
	}
	
	@Override
	public Income generateIncome( Order order) {
		Income income = new Income();
		income.setIncomeDate(order.getDeliveryDate());
		income.setIncomeOrder(order);
		income.setIncomeProducer(order.getCustomer());
		income.setIncomeValue(order.getTotalPrice());
		return incomeRepository.saveAndFlush(income);
	}

}
