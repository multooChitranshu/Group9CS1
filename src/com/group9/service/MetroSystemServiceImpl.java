package com.group9.service;

import com.group9.bean.MetroCard;
import com.group9.persistence.MetroCardDAOImpl;

public class MetroSystemServiceImpl implements MetroSystemService {
	
	MetroCardDAOImpl metroCardDAOImpl = new MetroCardDAOImpl();

	@Override
	public boolean addCard(MetroCard card) {
		MetroCard metroCard = null;
		metroCard = metroCardDAOImpl.searchCard(card.getAadharID());
		if(metroCard == null)
			return metroCardDAOImpl.addCard(card);
		return false;
	}

	@Override
	public double cardBalance(long cardId) {
		return metroCardDAOImpl.getCardBalance(cardId);
	
	}

}
