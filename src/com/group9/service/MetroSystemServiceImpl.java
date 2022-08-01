package com.group9.service;

import java.util.ArrayList;

import com.group9.bean.MetroCard;
import com.group9.bean.Transaction;
import com.group9.persistence.MetroCardDAOImpl;
import com.group9.persistence.MetroStationDAOImpl;
import com.group9.persistence.TransactionDAOImpl;

public class MetroSystemServiceImpl implements MetroSystemService {
	
	MetroCardDAOImpl metroCardDAOImpl = new MetroCardDAOImpl();
	TransactionDAOImpl transactionDAOImpl=new TransactionDAOImpl();
	MetroStationDAOImpl metroStationDAOImpl = new MetroStationDAOImpl();

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
	public boolean swipeIn(long cardId,int sourceStationId) {
		//user existed then only this function called
		if(metroStationDAOImpl.isValidStation(sourceStationId))
			if(cardBalance(cardId)>=20)
				return transactionDAOImpl.swipeIn(cardId, sourceStationId);
		return false;

	}

	@Override
	public boolean swipeOut(long cardId,int destinationStationId) {
		if(metroStationDAOImpl.isValidStation(destinationStationId)) {
			int sourceStationId=lastTransaction(cardId).getSourceStationId();
			double fare=checkFare(sourceStationId,destinationStationId);
			if(transactionDAOImpl.swipeOut(cardId, destinationStationId,fare)) {
				metroCardDAOImpl.rechargeCard(cardId, -fare);
				return true;
			}
		}
			return false;

	}

	@Override
	public double rechargeCard(long cardId,double amt) {
		 metroCardDAOImpl.rechargeCard(cardId, amt);
			 return cardBalance(cardId);
	}

	@Override
	public ArrayList<Transaction> transactionHistory(long cardId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction lastTransaction(long cardId) {
		return transactionDAOImpl.lastTransaction(cardId);
	}

	@Override
	public boolean checkCard(long cardId) {
		return metroCardDAOImpl.isValidCard(cardId);
	}

	@Override
	public double checkFare(int sourceStationId, int destinationStationId) {
		double fare=Math.abs(destinationStationId-sourceStationId)*5;
		return fare;
	}

	

}
