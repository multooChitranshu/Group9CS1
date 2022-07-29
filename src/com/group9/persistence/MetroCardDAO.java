package com.group9.persistence;

import com.group9.bean.*;

public interface MetroCardDAO {
	boolean addCard(MetroCard metroCard);
	MetroCard searchCard(long cardId);
	double getCardBalance(long cardId);
}
