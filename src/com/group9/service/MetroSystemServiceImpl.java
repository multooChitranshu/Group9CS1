package com.group9.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.group9.bean.MetroCard;
import com.group9.bean.MetroStation;
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
	public String swipeOut(long cardId,int destinationStationId) {
		String result="";
		if(metroStationDAOImpl.isValidStation(destinationStationId)) {
			int sourceStationId=lastTransaction(cardId).getSourceStationId();
			double fare=0, fine=0;
//			if source and destination are same, a fine of Rs. 30 is levied
			if(sourceStationId==destinationStationId) {
				fare=0;
				fine=30;
			}
			else {
				fare=calculateFare(sourceStationId,destinationStationId);
			}
			fine+=calculateFine(cardId, sourceStationId, destinationStationId);
			double totalCharge=fare+fine;
			if(cardBalance(cardId)>=totalCharge) {
				if(transactionDAOImpl.swipeOut(cardId, destinationStationId,totalCharge)) {
					metroCardDAOImpl.rechargeCard(cardId, -totalCharge);
					result="Swipe-Out successful! Rs."+totalCharge+" was deducted.";
					if(fine>0) {
						result+="( includes Rs."+fine+" fine)";
					}
					return result;
				}
			}
			result="Insufficient balance. Please recharge. Fare: Rs."+fare+", Fine levied: Rs."+fine+" Current balance: Rs"+cardBalance(cardId);
		}
		else {
			result="Swipe-out unsuccessful! Invalid station ID";
		}
		return result;
	}

	@Override
	public double rechargeCard(long cardId,double amt) {
		 metroCardDAOImpl.rechargeCard(cardId, amt);
			 return cardBalance(cardId);
	}

	@Override
	public List<Transaction> transactionHistory(long cardId) {
		List<Transaction>transactionList=transactionDAOImpl.transactionHistory(cardId);
		return transactionList;
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
	public double calculateFare(int sourceStationId, int destinationStationId) {
		return Math.abs(destinationStationId-sourceStationId)*5;
	}

	@Override
	public double calculateFine(long cardId, int sourceStationId, int destinationStationId) {
		Transaction lastTransaction=transactionDAOImpl.lastTransaction(cardId);
		int noOfStationsCovered=Math.abs(destinationStationId-sourceStationId);
//		assuming time to travel from a station to the immediate next station is 30 mins.
//		If more time taken, then charge Rs. 30 for every extra 30 mins
		long countOf30mins=lastTransaction.getDateAndTimeOfBoarding().until(LocalDateTime.now(), ChronoUnit.MINUTES)/30;
		return Math.max(0,(countOf30mins-noOfStationsCovered)*30);
	}

	@Override
	public MetroStation getStation(int stationId) {
		return metroStationDAOImpl.getStation(stationId);
	}

	

}
