package com.group9.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.group9.bean.MetroStation;

public class MetroStationDAOImpl implements MetroStationDAO {

	@Override
	public boolean isValidStation(int stationId) {
		if(getStation(stationId)!=null) {
			return true;
		}
		return false;
	}

	@Override
	public MetroStation getStation(int stationId) {
		try(Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/MetroDataBase", "root", "wiley")){
			PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM STATION WHERE stationId=?;");
			preparedStatement.setInt(1, stationId);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				int stationID=resultSet.getInt(1);
			    String stationName=resultSet.getString(2);
			    int previousStationId=resultSet.getInt(3);
			    int nextStationId=resultSet.getInt(4);
				return new MetroStation(stationID,stationName,previousStationId,nextStationId);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
				
		return null;
	}


}
