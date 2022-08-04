package com.group9.bean;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Transaction {
	private long transactionId;
	private long cardId;
	private int sourceStationId;
	private LocalDateTime dateAndTimeOfBoarding;
	private int destinationStationId;
	private LocalDateTime dateAndTimeOfExit;
    private double fare;
}

