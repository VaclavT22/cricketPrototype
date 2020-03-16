package com.taska.java.crickets.cricketfarm.serviceimpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taska.java.crickets.cricketfarm.model.BoughtItem;
import com.taska.java.crickets.cricketfarm.repository.BoughtItemRepository;
import com.taska.java.crickets.cricketfarm.service.BoughtItemService;

@Service
public class BoughtItemServiceImpl implements BoughtItemService {

	@Autowired
	private BoughtItemRepository boughtItemRepository;
	
	@Override
	public List<BoughtItem> getBoughtItems() {
		return boughtItemRepository.findAll();
	}

	@Override
	public BoughtItem getBoughtItem(Long id) {
		return boughtItemRepository.getOne(id);
	}

	@Override
	public BoughtItem addBoughtItem(BoughtItem boughtItem) {
		return boughtItemRepository.saveAndFlush(boughtItem);
	}

	@Override
	public BoughtItem updateBoughtItem(Long id, BoughtItem boughtItem) {
		BoughtItem existingBoughtItem = getBoughtItem(id);
		BeanUtils.copyProperties(boughtItem, existingBoughtItem, "id");
		return addBoughtItem(existingBoughtItem);
	}

	@Override
	public void deleteBoughtItem(Long id) {
		boughtItemRepository.deleteById(id);
	}

}
