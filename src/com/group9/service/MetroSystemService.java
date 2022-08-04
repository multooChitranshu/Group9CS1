package com.group9.service;

import java.util.List;

import com.group9.bean.MetroCard;
import com.group9.bean.MetroStation;
import com.group9.bean.Transaction;

public interface MetroSystemService {
	
	boolean addCard(MetroCard card);
	double cardBalance(long cardId);
	boolean swipeIn(long cardId,int sourceStationId);
	String swipeOut(long cardId,int destinationStationId);
	double rechargeCard(long cardId,double amt);
	List<Transaction> transactionHistory(long cardId);
	Transaction lastTransaction(long cardId);
	boolean checkCard(long cardId);
	double calculateFare(int sourceStationId,int destinationStationId);
	double calculateFine(long cardId,int sourceStationId,int destinationStationId);
	MetroStation getStation(int stationId);
}
