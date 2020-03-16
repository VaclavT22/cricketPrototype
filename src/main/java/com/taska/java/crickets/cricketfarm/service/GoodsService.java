package com.taska.java.crickets.cricketfarm.service;

import java.util.List;

import com.taska.java.crickets.cricketfarm.model.Goods;

public interface GoodsService {

	public List<Goods> getAllGoods();
	public Goods getGoods(Long id);
	public Goods addGoods(Goods goods);
	public Goods updateGoods(Long id, Goods goods);
	public void deleteGoods(Long id);
}
