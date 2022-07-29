package com.group9.service;

import java.util.ArrayList;

import com.group9.bean.MetroCard;
import com.group9.bean.Transaction;

public interface MetroSystemService {
	
	boolean addCard(MetroCard card);
	double cardBalance(long cardId);
	boolean swipeIn(long cardId);
	boolean swipeOut(long cardId);
	double rechargeCard(long cardId,double amt);
	ArrayList<Transaction> transactionHistory(long cardId);
	Transaction lastTrasaction(long cardId);

}
