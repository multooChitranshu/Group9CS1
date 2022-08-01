package com.group9.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.group9.bean.Transaction;

public class TransactionDAOImpl implements TransactionDAO {

	MetroStationDAO metroStationDAOImpl=new MetroStationDAOImpl();
	
	@Override
	public boolean swipeIn(long cardId, int sourceStationId) {
		if(metroStationDAOImpl.isValidStation(sourceStationId)) {
			try(Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/MetroDataBase", "root", "wiley")){
				PreparedStatement preparedStatement=conn.prepareStatement("INSERT INTO TRANSACTION VALUES(?,?,?);");
				
				preparedStatement.setLong(1, cardId);
				preparedStatement.setInt(2, sourceStationId);
				preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean swipeOut(long cardId, int destinationStationId, double fare) {
		// the validating station part should go in service layer
		if(metroStationDAOImpl.isValidStation(destinationStationId)) {
			try(Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/MetroDataBase", "root", "wiley")){
				PreparedStatement preparedStatement=conn.prepareStatement("UPDATE TRANSACTION SET destinationStationId=?,dateAndTimeOfExit=?,fare=? WHERE cardId=(SELECT * FROM (SELECT cardId FROM TRANSACTION WHERE cardId=? AND destinationStationId=null) as X);");
				preparedStatement.setInt(1, destinationStationId);
				preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
				preparedStatement.setDouble(3, fare);
				preparedStatement.setLong(4, destinationStationId);
				int result=preparedStatement.executeUpdate();
				if(result==1) {
					return true;
				}
				
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public List<Transaction> transactionHistory(long cardId) {
		List<Transaction> transactionList=new ArrayList<>();
		try {
			Class.forName("java.sql.Driver.class");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try(Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/MetroDataBase", "root", "wiley")){
			PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM TRANSACTION WHERE CARDID=? ORDER BY dateAndTimeOfBoarding DESC;");
			preparedStatement.setLong(1, cardId);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				long cardID=resultSet.getLong(1);
				int sourceStationId=resultSet.getInt(2);
				LocalDateTime dateAndTimeOfBoarding=resultSet.getDate(3)
						.toInstant().atZone(ZoneId.systemDefault())
					      .toLocalDateTime();
				int destinationStationId=resultSet.getInt(4);
				LocalDateTime dateAndTimeOfExit=resultSet.getDate(5)
						.toInstant().atZone(ZoneId.systemDefault())
					      .toLocalDateTime();
			    double fare=resultSet.getDouble(6);
			    
			    transactionList.add(new Transaction(cardID,sourceStationId,dateAndTimeOfBoarding,
			    		destinationStationId,dateAndTimeOfExit,fare));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Transaction lastTransaction(long cardId) {
		
		Transaction lastTransac=null;
		try(Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/MetroDataBase", "root", "wiley")){
			
			PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM TRANSACTION ORDER BY dateAndTimeOfBoarding DESC LIMIT 1;");
			
			ResultSet resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				long cardID=resultSet.getLong(1);
				int sourceStationId=resultSet.getInt(2);
				LocalDateTime dateAndTimeOfBoarding=resultSet.getDate(3)
						.toInstant().atZone(ZoneId.systemDefault())
					      .toLocalDateTime();
				int destinationStationId=resultSet.getInt(4);
				LocalDateTime dateAndTimeOfExit=resultSet.getDate(5)
						.toInstant().atZone(ZoneId.systemDefault())
					      .toLocalDateTime();
			    double fare=resultSet.getDouble(6);
			    
			    lastTransac=new Transaction(cardID,sourceStationId,dateAndTimeOfBoarding,
			    		destinationStationId,dateAndTimeOfExit,fare);
			}
			return lastTransac;
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
