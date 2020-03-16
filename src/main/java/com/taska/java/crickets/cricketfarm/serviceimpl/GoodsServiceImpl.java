package com.taska.java.crickets.cricketfarm.serviceimpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taska.java.crickets.cricketfarm.model.Goods;
import com.taska.java.crickets.cricketfarm.repository.GoodsRepository;
import com.taska.java.crickets.cricketfarm.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsRepository goodsRepository;
	
	@Override
	public List<Goods> getAllGoods() {
		return goodsRepository.findAll();
	}

	@Override
	public Goods getGoods(Long id) {
		return goodsRepository.getOne(id);
	}

	@Override
	public Goods addGoods(Goods goods) {
		return goodsRepository.saveAndFlush(goods);
	}

	@Override
	public Goods updateGoods(Long id, Goods goods) {
		Goods existingGoods = getGoods(id);
		BeanUtils.copyProperties(goods, existingGoods, "id");
		return addGoods(existingGoods);
	}

	@Override
	public void deleteGoods(Long id) {
		goodsRepository.deleteById(id);
	}

}
