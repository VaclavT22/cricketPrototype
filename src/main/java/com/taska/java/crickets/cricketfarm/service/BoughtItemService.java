package com.taska.java.crickets.cricketfarm.service;

import java.util.List;

import com.taska.java.crickets.cricketfarm.model.BoughtItem;

public interface BoughtItemService {
	public List<BoughtItem> getBoughtItems();
	public BoughtItem getBoughtItem(Long id);
	public BoughtItem addBoughtItem(BoughtItem boughtItem);
	public BoughtItem updateBoughtItem(Long id, BoughtItem boughtItem);
	public void deleteBoughtItem(Long id);
}
