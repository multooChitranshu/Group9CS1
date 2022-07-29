package com.group9.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetroStation {
	private int stationId;
    private String stationName;
    private int previousStationId;
    private int nextStationId;

}
