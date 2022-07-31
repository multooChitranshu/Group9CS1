package com.group9.persistence;

import com.group9.bean.MetroStation;

public interface MetroStationDAO {
	boolean isValidStation(int stationId);
	MetroStation getStation(int stationId);
}
