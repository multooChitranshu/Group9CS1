package com.group9.service;

import com.group9.bean.MetroCard;

public interface MetroSystemService {
	
	boolean addCard(MetroCard card);
	double cardBalance(long cardId);

}
