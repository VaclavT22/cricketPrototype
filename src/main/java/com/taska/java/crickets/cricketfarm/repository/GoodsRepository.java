package com.taska.java.crickets.cricketfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taska.java.crickets.cricketfarm.model.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

}
