package com.taska.java.crickets.cricketfarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taska.java.crickets.cricketfarm.model.Income;
import com.taska.java.crickets.cricketfarm.service.IncomeService;

@RestController
@RequestMapping("/api/incomes")
@CrossOrigin(origins = "http://localhost:3000")
public class IncomeController {

	@Autowired
	private IncomeService incomeService;
	
	@GetMapping
	public List<Income> getIncomes(){
		return incomeService.getIncomes();
	}
	
	@GetMapping("/{id}")
	public Income getIncome(@PathVariable("id") Long id) {
		return incomeService.getIncome(id);
	}
}
