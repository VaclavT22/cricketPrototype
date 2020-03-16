package com.taska.java.crickets.cricketfarm.serviceimpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taska.java.crickets.cricketfarm.model.LineItem;
import com.taska.java.crickets.cricketfarm.repository.LineItemRepository;
import com.taska.java.crickets.cricketfarm.service.LineItemService;
import com.taska.java.crickets.cricketfarm.service.OrderService;

@Service
public class LineItemServiceImpl implements LineItemService {
	
	@Autowired
	private OrderService orderService;
	
	
	@Autowired
	private LineItemRepository lineItemRepository;
	
	
	@Override
	public void updateOrderPrice(Long orderId, Long productId, int amount) {
		orderService.updateOrderTotalPrice(orderId, productId, amount);
	}


	@Override
	public List<LineItem> getLineItems() {
		return lineItemRepository.findAll();
	}


	@Override
	public LineItem getLineItemById(Long id) {
		return lineItemRepository.getOne(id);
	}


	@Override
	public LineItem saveLineItem(LineItem lineItem) {
		updateOrderPrice(lineItem.getOrderId().getId(), lineItem.getProductId().getId(), lineItem.getAmount());
		return lineItemRepository.saveAndFlush(lineItem);
	}


	@Override
	public LineItem updateLineItem(Long id, LineItem lineItem) {
		LineItem existingLineItem = lineItemRepository.getOne(id);
		BeanUtils.copyProperties(lineItem, existingLineItem, "id");
		return lineItemRepository.saveAndFlush(existingLineItem);
	}


	@Override
	public void deleteLineItem(Long id) {
		lineItemRepository.deleteById(id);
	}


	@Override
	public List<LineItem> getLineItemsByOrderId(Long id) {
		return lineItemRepository.findByOrderId(orderService.getOrderById(id));
	}
	
}
