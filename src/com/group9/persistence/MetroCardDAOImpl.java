package com.group9.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.group9.bean.MetroCard;

public class MetroCardDAOImpl implements MetroCardDAO {

	@Override
	public boolean addCard(MetroCard metroCard) {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/metrodatabase", "root",
				"wiley");
				PreparedStatement preparedStatement = connection
						.prepareStatement("INSERT INTO CARD values(?,?,?)");) {

			preparedStatement.setLong(1, metroCard.getCardID());
			preparedStatement.setLong(2,metroCard.getAadharID() );
			preparedStatement.setDouble(3, metroCard.getBalance());

			int rows = preparedStatement.executeUpdate();

			if(rows!=0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean isValidCard(long cardId) {
		if(searchCard(cardId)!=null)
			return true;
		return false;
	}


	@Override
	public MetroCard searchCard(long cardId) {
		MetroCard card=null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/metrodatabase", "root",
				"wiley");
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM CARD where CARDID=?");) {

			preparedStatement.setLong(1, cardId);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				long id = resultSet.getLong("cardId");
				long aadhar = resultSet.getLong("aadharId");
				double balance = resultSet.getDouble("balance");

				card = new MetroCard(id, aadhar,balance);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return card;
	}
	
	@Override
	public double getCardBalance(long cardId) {
		MetroCard newCard=searchCard(cardId);
		return newCard.getBalance();
	}

	@Override
	public boolean rechargeCard(long cardId, double money) {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/metrodatabase", "root",
				"wiley");
				PreparedStatement preparedStatement = connection
						.prepareStatement("UPDATE CARD SET BALANCE=BALANCE+? where CARDID=?");) {

			preparedStatement.setDouble(1, money);
			preparedStatement.setLong(2, cardId);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				long id = resultSet.getLong("cardId");
				long aadhar = resultSet.getLong("aadharId");
				double balance = resultSet.getDouble("balance");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	
}
