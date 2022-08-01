package com.group9.service;

import java.util.ArrayList;
import java.util.List;

import com.group9.bean.MetroCard;
import com.group9.bean.Transaction;
import com.group9.persistence.MetroCardDAOImpl;
import com.group9.persistence.TransactionDAOImpl;

public class MetroSystemServiceImpl implements MetroSystemService {
	
	MetroCardDAOImpl metroCardDAOImpl = new MetroCardDAOImpl();
	TransactionDAOImpl transactionDAOImpl =new TransactionDAOImpl();
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

	@Override
	public boolean swipeIn(long cardId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean swipeOut(long cardId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double rechargeCard(long cardId,double amt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Transaction> transactionHistory(long cardId) {
		List<Transaction>transactionList=transactionDAOImpl.transactionHistory(cardId);
		return transactionList;
	}

	@Override
	public Transaction lastTrasaction(long cardId) {
		Transaction lastTransaction=transactionDAOImpl.lastTransaction(cardId);
		return lastTransaction;
	}

	

}
