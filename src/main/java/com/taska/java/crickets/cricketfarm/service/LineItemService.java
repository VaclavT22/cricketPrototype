package com.taska.java.crickets.cricketfarm.service;

import java.util.List;

import com.taska.java.crickets.cricketfarm.model.LineItem;

public interface LineItemService {
	
	public List<LineItem> getLineItems();
	public LineItem getLineItemById(Long id);
	public LineItem saveLineItem(LineItem lineItem);
	public LineItem updateLineItem(Long id, LineItem lineItem);
	public void deleteLineItem(Long id);
	public List<LineItem> getLineItemsByOrderId(Long id);
	public void updateOrderPrice(Long orderId, Long productId, int amount);
	
}
