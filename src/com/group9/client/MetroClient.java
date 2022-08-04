package com.group9.client;

import java.util.Scanner;

import com.group9.presentation.MetroSystemPresentationImpl;

public class MetroClient {

	public static void main(String[] args) {
		MetroSystemPresentationImpl metroPresentation=new MetroSystemPresentationImpl();
		Scanner scanner=new Scanner(System.in);
		while(true) {
			System.out.println("-------Menu-------");
			metroPresentation.showMenu();
			System.out.println("Enter Choice ");
			char choice=scanner.next().toUpperCase().charAt(0);
			metroPresentation.performMenu(choice);
			System.out.println();
					
		}
	}

}
