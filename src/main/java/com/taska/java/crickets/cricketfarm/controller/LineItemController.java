package com.taska.java.crickets.cricketfarm.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taska.java.crickets.cricketfarm.model.LineItem;
import com.taska.java.crickets.cricketfarm.repository.LineItemRepository;
import com.taska.java.crickets.cricketfarm.service.LineItemService;


@RestController
@RequestMapping("/api/line_item")
@CrossOrigin(origins = "http://localhost:3000")
public class LineItemController {
	
	@Autowired
	private LineItemRepository lineItemRepository;
	
	@Autowired
	private LineItemService lineItemService;
	
	@GetMapping
	public List<LineItem> getLineItems(){
		return lineItemRepository.findAll();
	}

	@GetMapping("/{id}")
	public LineItem getLineItem(@PathVariable("id") Long id) {
		return lineItemRepository.getOne(id);
	}
	
	@PostMapping
	public LineItem createLineItem(@RequestBody final LineItem lineItem) {
		lineItemService.updateOrderPrice(lineItem.getOrderId().getId(), lineItem.getProductId().getId(), lineItem.getAmount());
		return lineItemRepository.saveAndFlush(lineItem);
	}
	
	@PutMapping("/{id}")
	public LineItem updateLineItem(@RequestBody final LineItem lineItem, @PathVariable("id") Long id) {
		LineItem existingLineItem = lineItemRepository.getOne(id);
		BeanUtils.copyProperties(lineItem, existingLineItem, "id");
		return lineItemRepository.saveAndFlush(existingLineItem);
	}
	
	@DeleteMapping("/{id}")
	public void deleteLineItem(@PathVariable("id") Long id) {
		lineItemRepository.deleteById(id);
	}
}
