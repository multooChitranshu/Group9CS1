package com.group9.service;

import java.util.List;

import com.group9.bean.MetroCard;
import com.group9.bean.Transaction;

public interface MetroSystemService {
	
	boolean addCard(MetroCard card);
	double cardBalance(long cardId);
	boolean swipeIn(long cardId,int sourceStationId);
	boolean swipeOut(long cardId,int destinationStationId);
	double rechargeCard(long cardId,double amt);
	List<Transaction> transactionHistory(long cardId);
	Transaction lastTransaction(long cardId);
	boolean checkCard(long cardId);
	double checkFare(int sourceStationId,int destinationStationId);
}
