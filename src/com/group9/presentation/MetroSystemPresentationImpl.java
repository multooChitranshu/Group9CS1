package com.group9.presentation;
import java.util.InputMismatchException;
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
		System.out.println("0. EXIT");
	}
	
	private void existingUserMenu(long id) {
		Scanner scanner=new Scanner(System.in);
		if(id==0) {
			System.out.println("Enter your card Id : ");
			try {
				id=scanner.nextLong();
			}
			catch(InputMismatchException e) {
				System.out.println("ERROR! Please enter a valid aadhar ID");
				return;
			}
		}
		int val=0;
		//checking valid user
		while(true) {
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
				System.out.println("5. Sign out");
				System.out.println("0. EXIT");
	//			sign out to perform menu
				System.out.println("Enter your choice: ");
				char ch =scanner.next().charAt(0);
				switch (ch) {
				case '1':
					switch (val) {
					case 1:
						System.out.println("Enter source station id : ");
						System.out.println("1. Bhopal");
						System.out.println("2. MP Nagar");
						System.out.println("3. Indrapuri");
						System.out.println("4. New Market");
						System.out.println("5. Board Office");
						int ssi=scanner.nextInt();
						try {
							if(metroServiceImpl.swipeIn(id,ssi))
								System.out.println("Swipe-In successful");
							else
								System.out.println("Swipe-In Failed!");
						}
						catch(InputMismatchException e) {
							System.out.println("Please enter a valid station ID from the above list.");
						}
						break;
					case 2:
						System.out.println("Enter destination station id : ");
						System.out.println("1. Bhopal");
						System.out.println("2. MP Nagar");
						System.out.println("3. Indrapuri");
						System.out.println("4. New Market");
						System.out.println("5. Board Office");
						try {
							int dsi=scanner.nextInt();
							System.out.println(metroServiceImpl.swipeOut(id,dsi));
						}
						catch(InputMismatchException e) {
							System.out.println("Please enter a valid station ID from the above list.");
						}
						break;
					}
					break;
				case '2':
					double bal=metroServiceImpl.cardBalance(id);
					System.out.println("Available balance : "+bal);
					break;
				case '3':
					double amt=metroServiceImpl.cardBalance(id);
					System.out.print("Your current balance is Rs."+amt);
					System.out.print(" Enter the add-on amount : ");
					try {
					amt=Double.parseDouble(scanner.next());
					if(amt<=0)
						throw new NumberFormatException();
					else
						metroServiceImpl.rechargeCard(id,amt);
					amt=metroServiceImpl.cardBalance(id);
					System.out.println("Your updated balance is Rs."+amt);
					}
					catch (NumberFormatException e) {
						System.out.println("Enter valid amount(> 0) please...");
					}
					break;
				case '4':
					List<Transaction> history=metroServiceImpl.transactionHistory(id);
					if(history==null || history.isEmpty()) {
						System.out.println("No previous transactions to show");
						break;
					}
			        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
			        System.out.printf("%5s %10s %20s %25s %20s %25s %10s", "S.No |", "CardID |", "Source Station |", "Date&Time of Boarding |","Destination Station |", "Date&Time of Exit |", "Total Charges |");
			        System.out.println();
			        int sno=0;
					for(Transaction tr:history){
						sno++;
						try {
							System.out.printf("%5d %10d %20s %25s %20s %25s %10.2f",sno,tr.getCardId(),metroServiceImpl.getStation(tr.getSourceStationId()).getStationName(),
									tr.getDateAndTimeOfBoarding(),metroServiceImpl.getStation(tr.getDestinationStationId()).getStationName(),tr.getDateAndTimeOfExit(),tr.getFare());
							System.out.println();
						}
						catch(NullPointerException e) {
						}
					}
			        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------");
					break;
				case '5':
					System.out.println("Sign-out successful!");
					return;
				case '0':
					System.out.println("Thanks for using Metro System");
					System.exit(0);
				default :
					System.out.println("Invalid Choice");
				}
			}
			else {
				System.out.println("Card does not exists. Please check the card number again!");
				break;
			}
		}
	}
	
	@Override
	public void performMenu(char choice) {
		Scanner scanner=new Scanner(System.in);
		long id;
		switch (choice) {
		case 'Y':
			MetroCard metroCard=new MetroCard();
			System.out.println("Enter Aadhar ID: ");
			long aadhar;
			try {
				aadhar=scanner.nextLong();
				metroCard.setAadharID(aadhar);
				metroCard.setCardID(aadhar);
				metroCard.setBalance(100);
			}
			catch(InputMismatchException e) {
				System.out.println("ERROR! Please enter a valid aadhar ID");
				break;
			}
			if(metroServiceImpl.addCard(metroCard)) {
				System.out.println("Welcome to our Metro System.\nYour card added successfully");
				System.out.println("Your card details are: "+metroCard);
				//swipe-in and check balance
				System.out.println("Press 1 to continue to booking");
				System.out.println("Press any other key to sign out");
				String temp_choice=scanner.next();
				if(temp_choice.charAt(0)=='1') {
					existingUserMenu(aadhar);
				}
				else{
					System.out.println("Sign-out successful!");
					break;
				}
			}
			else {
				System.out.println("Failed to add the card. Card already exists!");
			}
			break;
		case 'N':
			existingUserMenu(0);
			break;
		case '0':
			System.out.println("Thanks for using Metro System");
			System.exit(0);
		default:
			System.out.println("Invalid Choice");
		
	 }
	}
}
