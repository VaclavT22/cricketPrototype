package com.taska.java.crickets.cricketfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taska.java.crickets.cricketfarm.model.Outcome;

public interface OutcomeRepository extends JpaRepository<Outcome, Long> {

}
