package com.taska.java.crickets.cricketfarm.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Entity
public class BoughtItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "purchaseId")
	private Purchase purchaseId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "goodsId")
	private Goods goods;
	private Integer amount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Purchase getPurchase() {
		return purchaseId;
	}
	public void setPurchase(Purchase purchaseId) {
		this.purchaseId = purchaseId;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	
}
