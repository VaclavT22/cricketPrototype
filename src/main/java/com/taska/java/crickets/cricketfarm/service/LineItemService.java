package com.taska.java.crickets.cricketfarm.service;

public interface LineItemService {
	public void updateOrderPrice(Long orderId, Long productId, int amount);
}
