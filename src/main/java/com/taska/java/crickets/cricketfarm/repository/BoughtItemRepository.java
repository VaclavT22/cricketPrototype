package com.taska.java.crickets.cricketfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taska.java.crickets.cricketfarm.model.BoughtItem;

public interface BoughtItemRepository extends JpaRepository<BoughtItem, Long>{

}
