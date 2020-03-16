package com.taska.java.crickets.cricketfarm.service;

import java.util.List;

import com.taska.java.crickets.cricketfarm.model.Outcome;
import com.taska.java.crickets.cricketfarm.model.Purchase;

public interface OutcomeService {
	public List<Outcome> getOutcomes();
	public Outcome getOutcome(Long id);
	public void generateOutcome(Purchase purchase);
}
