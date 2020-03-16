package com.taska.java.crickets.cricketfarm.service;

import java.util.List;

import com.taska.java.crickets.cricketfarm.model.BoughtItem;
import com.taska.java.crickets.cricketfarm.model.Purchase;

public interface PurchaseService {
	public List<Purchase> getPurchases();
	public Purchase getPurchase(Long id);
	public Purchase addPurchase(Purchase purchase);
	public Purchase updatePurchase(Long id, Purchase purchase);
	public void deletePurchase(Long id);
	public void updatePurchasePrice(Long id, BoughtItem boughtItem);
	public void completePurchase(Long id);
}
