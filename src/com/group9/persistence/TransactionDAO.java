package com.group9.persistence;
import java.util.List;

import com.group9.bean.Transaction;

public interface TransactionDAO {
	boolean swipeIn(long cardId, int sourceStationId);
	boolean swipeOut(long cardId, int destinationStationId, double fare);
	List<Transaction> transactionHistory(long cardId);
	Transaction lastTransaction(long cardId);
}
