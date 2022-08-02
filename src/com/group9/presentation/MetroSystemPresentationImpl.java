package com.group9.presentation;
import java.util.List;
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
				//swipe-in and check balance
				int internal_choice;
				System.out.println("1. swipe-in");
				System.out.println("2. check balance");
				internal_choice=scanner.nextInt();
				switch (internal_choice) {
				case 1:
					//swipe in
					System.out.println("Enter source station id : ");
					int ssi=scanner.nextInt();
					if(metroServiceImpl.swipeIn(aadhar,ssi))
						System.out.println("Swipe-In successful");
					else
						System.out.println("Swipe-In un-successful");
					break;
				case 2:
					//check balance
					System.out.println("Available balance : "+metroServiceImpl.cardBalance(aadhar));
				}
			}
			else {
				System.out.println("Failed to add the card. Card already exists!");
			}
			break;
		case 'N':
			System.out.println("Enter your card Id : ");
			id=scanner.nextLong();
			int val=0;
			//checking valid user
			if(metroServiceImpl.checkCard(id)) {
				//calling transaction function
				Transaction t=metroServiceImpl.lastTransaction(id);
				//if entry closed (swipe-in)(val=1)
				if( t==null || (t.getDateAndTimeOfExit()!=null))
				{
					System.out.println("1. Swipe-In");
					val=1;
				}
				//if entry open (swipe-out)(val=2)
				else if(t.getDateAndTimeOfBoarding() !=null && t.getDateAndTimeOfExit()== null)
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
						System.out.println("Enter source station id : ");
						int ssi=scanner.nextInt();
						if(metroServiceImpl.swipeIn(id,ssi))
							System.out.println("Swipe-In successful");
						else
							System.out.println("Swipe-In un-successful");
						break;
					case 2:
						System.out.println("Enter destination station id : ");
						int dsi=scanner.nextInt();
						if(metroServiceImpl.swipeOut(id,dsi)) {
							System.out.println("Swipe-Out successful");
							System.out.println("Rs. "+metroServiceImpl.lastTransaction(id).getFare()+" was deducted. Thank you!");
						}
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
					if(amt<0)
						System.out.println("Enter valid amount please...");
					else
					metroServiceImpl.rechargeCard(id,amt);
					amt=metroServiceImpl.cardBalance(id);
					System.out.println("Available balance : "+amt);
					break;
				case 4:
					//incomplete
					List<Transaction> history=metroServiceImpl.transactionHistory(id);
					System.out.println(history);
					break;
				case 0:
					System.out.println("Thanks for using Metro System");
					System.exit(0);
				default :
					System.out.println("Invalid Choice");
				}
			}
			else {
				System.out.println("Card does not exists please check the card number again");
			}
			break;
		case '0':
			System.out.println("Thanks for using Metro System");
			System.exit(0);
		default:
			System.out.println("Invalid Choice");
		
	 }
	}
}
