package com.taska.java.crickets.cricketfarm.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taska.java.crickets.cricketfarm.model.Outcome;
import com.taska.java.crickets.cricketfarm.model.Purchase;
import com.taska.java.crickets.cricketfarm.repository.OutcomeRepository;
import com.taska.java.crickets.cricketfarm.service.OutcomeService;

@Service
public class OutcomeServiceImpl implements OutcomeService {

	@Autowired
	private OutcomeRepository outcomeRepository;

	@Override
	public List<Outcome> getOutcomes() {
		return outcomeRepository.findAll();
	}

	@Override
	public Outcome getOutcome(Long id) {
		return outcomeRepository.getOne(id);
	}

	@Override
	public void generateOutcome(Purchase purchase) {
		
	}
	
	
}
