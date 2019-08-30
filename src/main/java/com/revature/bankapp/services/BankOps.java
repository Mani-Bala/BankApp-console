package com.revature.bankapp.services;

import java.util.Scanner;

import com.revature.bankapp.services.LoginAc;
import com.revature.bankapp.services.Register;

public class BankOps {

	static Scanner sc =new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
		BankOps.welcomePage();
	}
	
	public static void welcomePage() throws Exception {

		System.out.println("\nWELCOME TO BANK OF AMERICA..");
		
		System.out.println("\n1. Register / Create an account");
		System.out.println("2. Log-in (Cardless Transaction)");
		System.out.println("3. Exit");
		System.out.println("\nEnter Your Choice :");
		int val=sc.nextInt();
		
		
			switch (val) {
			case 1:
				try {
					System.out.println("Enter your name :");
					String name=sc.next();
					System.out.println("Enter Mobile Number :");
					long mobno=sc.nextLong();
					System.out.println("Enter New Acccount Number :");
					long actno=sc.nextLong();
					System.out.println("Account type (Current/Saving) :");
					String actype=sc.next();
					System.out.println("Set your new pin Number :");
					int pin=sc.nextInt();
					
					if(actno>10000000 && actno<19999999 )
						Register.register(name,mobno,actno,actype,pin);
					else {
						System.out.println("Please enter the valid Ac.No between 10000000 and 19999999.");
						welcomePage();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			case 2:
				System.out.println("Enter the existing Acccount Number :");
				int actno=sc.nextInt();
				System.out.println("Enter your Pin Number :");
				int pin=sc.nextInt();
				
					LoginAc.login(actno,pin);
					break;
			case 3:
				System.out.println("\nTHANK YOU FOR USING OUR SERVICES.");
				break;
			}
	}

}
