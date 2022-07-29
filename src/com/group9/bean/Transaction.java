package com.group9.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Transaction {
	private String dateAndTimeOfBoarding;
	private String dateAndTimeOfExit;
    private int sourceStationId;
    private int destinationStationId;
    private double fare;
    private long cardId;
}

