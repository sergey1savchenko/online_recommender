package com.my.recommender.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.recommender.dao.RatingDao;

@Component
public class RatingServiceImpl {
	
	@Autowired
	private RatingDao ratingDao;
	
	
	
}
