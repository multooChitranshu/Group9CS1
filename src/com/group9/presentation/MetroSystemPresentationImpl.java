package com.group9.presentation;
import java.util.Scanner;

import com.group9.bean.MetroCard;
import com.group9.service.MetroSystemServiceImpl;
public class MetroSystemPresentationImpl implements MetroSystemPresentation {

	MetroSystemServiceImpl metroServiceImpl=new MetroSystemServiceImpl();
	@Override
	public void showMenu() {
		System.out.println("1. Swipe In");
		System.out.println("2. Swipe Out");
		System.out.println("3. Recharge Card");
		System.out.println("4. Transaction History");
		System.out.println("5. Exit");
	}

	@Override
	public void performMenu(int choice) {
		Scanner scanner=new Scanner(System.in);
		int id;
		switch (choice) {
		case 1:
			MetroCard metroCard=new MetroCard();
			
			System.out.println("Are you a new user?");
			System.out.println("Press Y for YES, N for No");
			char ch=scanner.next().charAt(0);
			ch=Character.toUpperCase(ch);
			switch(ch) {
			case 'Y':
				System.out.println("Enter Aadhar ID: ");
				long aadhar=scanner.nextLong();
				metroCard.setAadharID(aadhar);
				metroCard.setCardID(aadhar);
				metroCard.setBalance(100);
				if(metroServiceImpl.addCard(metroCard)) {
					System.out.println("Welcome to our Metro System.\nYour card added successfully");
					System.out.println("Your card details are: "+metroCard);
					
					
				}
				else {
					System.out.println("Failed to add the card. Card already exists!");
				}
				break;
			case 'N':
				System.out.println("Feature under construction");
				break;
			default:
				System.out.println("Inavalid choice");	
			}
			break;
		case 2:
			System.out.println("Feature under construction");
			break;
		case 3:
			System.out.println("Feature under construction");
			break;
		case 4:
			System.out.println("Feature under construction");
			break;
		case 5:
			System.out.println("Thanks for using Metro System");
			System.exit(0);
		default:
			System.out.println("Invalid Choice");
		
	}

}}
