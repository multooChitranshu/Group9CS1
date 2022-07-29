package com.group9.presentation;
import java.util.ArrayList;
import java.util.Scanner;

import com.group9.bean.MetroCard;
import com.group9.bean.Transaction;
import com.group9.service.MetroSystemServiceImpl;
public class MetroSystemPresentationImpl implements MetroSystemPresentation {

	MetroSystemServiceImpl metroServiceImpl=new MetroSystemServiceImpl();
	@Override
	public void showMenu() {
		System.out.println("Are you a new user?");
		System.out.println("Press Y for YES, N for No");
		System.out.println("0. Exit");
	}
	
	@Override
	public void performMenu(char choice) {
		Scanner scanner=new Scanner(System.in);
		long id;
		switch (choice) {
		case 'Y':
			MetroCard metroCard=new MetroCard();
			System.out.println("Enter Aadhar ID: ");
			long aadhar=scanner.nextLong();
			metroCard.setAadharID(aadhar);
			metroCard.setCardID(aadhar);
			metroCard.setBalance(100);
			if(metroServiceImpl.addCard(metroCard)) {
				System.out.println("Welcome to our Metro System.\nYour card added successfully");
				System.out.println("Your card details are: "+metroCard);
				//swipe-in called
				if(metroServiceImpl.swipeIn(aadhar))
					System.out.println("Swipe-In successful");
				else
					System.out.println("Swipe-In un-successful");
				break;
			}
			else {
				System.out.println("Failed to add the card. Card already exists!");
			}
			break;
		case 'N':
			System.out.println("Enter your card Id : ");
			id=scanner.nextLong();
			int val=0;
			//calling transaction function
			Transaction t=metroServiceImpl.lastTrasaction(id);
			//if entry closed (swipe-in)(val=1)
			if(t.getSourceStationId()!= -1 && t.getDestinationStationId()!=-1)
			{
				System.out.println("1. Swipe-In");
				val=1;
			}
			//if entry open (swipe-out)(val=2)
			if(t.getSourceStationId() !=-1 && t.getDestinationStationId()== -1)
			{
				System.out.println("1. Swipe-Out");
				val=2;
			}
			//check balance
			System.out.println("2. Check Balance");
			//recharge card
			System.out.println("3. Recharge Card");
			//transaction table
			System.out.println("4. Get all previous transactions");
			//exit
			System.out.println("Enter your choice: ");
			int ch =scanner.nextInt();
			switch (ch) {
			case 1:
				switch (val) {
				case 1:
					if(metroServiceImpl.swipeIn(id))
						System.out.println("Swipe-In successful");
					else
						System.out.println("Swipe-In un-successful");
					break;
				case 2:
					if(metroServiceImpl.swipeOut(id))
						System.out.println("Swipe-Out successful");
					else
						System.out.println("Swipe-Out un-successful");
					break;
				}
				break;
			case 2:
				double bal=metroServiceImpl.cardBalance(id);
				System.out.println("Available balance : "+bal);
				break;
			case 3:
				System.out.println("Enter the amount : ");
				double amt=scanner.nextDouble();
				metroServiceImpl.rechargeCard(id,amt);
				amt=metroServiceImpl.cardBalance(id);
				System.out.println("Available balance : "+amt);
				break;
			case 4:
				metroServiceImpl.transactionHistory(id);
				break;
			case 0:
				System.out.println("Thanks for using Metro System");
				System.exit(0);
			default :
				System.out.println("Invalid Choice");
			}
			break;
		case 0:
			System.out.println("Thanks for using Metro System");
			System.exit(0);
		default:
			System.out.println("Invalid Choice");
		
	}

}}
