package com.taska.java.crickets.cricketfarm.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taska.java.crickets.cricketfarm.model.BoughtItem;
import com.taska.java.crickets.cricketfarm.model.Purchase;
import com.taska.java.crickets.cricketfarm.repository.PurchaseRepository;
import com.taska.java.crickets.cricketfarm.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Override
	public List<Purchase> getPurchases() {
		return purchaseRepository.findAll();
	}

	@Override
	public Purchase getPurchase(Long id) {
		return purchaseRepository.getOne(id);
	}

	@Override
	public Purchase addPurchase(Purchase purchase) {
		return purchaseRepository.saveAndFlush(purchase);
	}

	@Override
	public Purchase updatePurchase(Long id, Purchase purchase) {
		Purchase existingPurchase = getPurchase(id);
		if(existingPurchase.getIsCompleted()) {
			return existingPurchase;
		}
		BeanUtils.copyProperties(purchase, existingPurchase, "id");
		return addPurchase(existingPurchase);
	}

	@Override
	public void deletePurchase(Long id) {
		purchaseRepository.deleteById(id);
	}

	@Override
	public void updatePurchasePrice(Long id, BoughtItem boughtItem) {
		Purchase purchase = getPurchase(id);
		if(purchase.getTotalPrice() == null) {
			BigDecimal newPrice = boughtItem.getGoods().getPrice().multiply(new BigDecimal(boughtItem.getAmount()));
			purchase.setTotalPrice(newPrice);
		} else {
			BigDecimal newPrice = purchase.getTotalPrice()
					.add(boughtItem.getGoods().getPrice()
							.multiply(new BigDecimal(boughtItem.getAmount())));
			purchase.setTotalPrice(newPrice);
		}
		updatePurchase(id, purchase);
	}

	@Override
	public void completePurchase(Long id) {
		Purchase toComplete = getPurchase(id);
		toComplete.setIsCompleted(true);
		updatePurchase(id, toComplete);
	}

}
